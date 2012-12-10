/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.Enum;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;

/**
 * Java source type
 */
final class SourceEnum
	extends SourceAbstractType<Enum>
	implements JavaResourceEnum
{

	private final Vector<JavaResourceEnumConstant> enumConstants;



	// ********** construction/initialization **********

	/**
	 * build top-level type
	 */
	static JavaResourceEnum newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			EnumDeclaration enumDeclaration) {
		Enum _enum = new JDTEnum(
			enumDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceEnum jre = new SourceEnum(javaResourceCompilationUnit, _enum);
		jre.initialize(enumDeclaration);
		return jre;
	}

	/**
	 * build nested type
	 */
	protected static JavaResourceEnum newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			Type declaringType,
			EnumDeclaration enumDeclaration,
			int occurrence) {
		Enum _enum = new JDTEnum(
				declaringType,
				enumDeclaration,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceEnum jre = new SourceEnum(javaResourceCompilationUnit, _enum);
		jre.initialize(enumDeclaration);
		return jre;
	}

	private SourceEnum(JavaResourceCompilationUnit javaResourceCompilationUnit, Enum _enum) {
		super(javaResourceCompilationUnit, _enum);
		this.enumConstants = new Vector<JavaResourceEnumConstant>();
	}

	protected void initialize(EnumDeclaration enumDeclaration) {
		super.initialize(enumDeclaration);
		this.initializeEnumConstants(enumDeclaration);
	}


	// ********** update **********

	public void synchronizeWith(EnumDeclaration enumDeclaration) {
		super.synchronizeWith(enumDeclaration);
		this.syncEnumConstants(enumDeclaration);
	}


	// ********** SourceAnnotatedElement implementation **********

	public void resolveTypes(EnumDeclaration enumDeclaration) {
		EnumConstantDeclaration[] enumConstantDeclarations = this.annotatedElement.getEnumConstants(enumDeclaration);
		int i = 0;
		for (JavaResourceEnumConstant enumConstant : this.getEnumConstants()) {
			enumConstant.resolveTypes(enumConstantDeclarations[i++]);
		}
	}


	// ******** JavaResourceAnnotatedElement implementation ********
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.ENUM;
	}
	
	
	// ********** enum constants **********

	public Iterable<JavaResourceEnumConstant> getEnumConstants() {
		return new LiveCloneIterable<JavaResourceEnumConstant>(this.enumConstants);
	}

	private void addEnumConstant(JavaResourceEnumConstant enumConstant) {
		this.addItemToCollection(enumConstant, this.enumConstants, ENUM_CONSTANTS_COLLECTION);
	}

	private JavaResourceEnumConstant getEnumConstant(String fieldName, int occurrence) {
		for (JavaResourceEnumConstant enumConstant : this.getEnumConstants()) {
			if (enumConstant.isFor(fieldName, occurrence)) {
				return enumConstant;
			}
		}
		return null;
	}

	private void removeEnumConstants(Collection<JavaResourceEnumConstant> remove) {
		this.removeItemsFromCollection(remove, this.enumConstants, ENUM_CONSTANTS_COLLECTION);
	}

	private void initializeEnumConstants(EnumDeclaration enumDeclaration) {
		EnumConstantDeclaration[] enumConstantDeclarations = this.annotatedElement.getEnumConstants(enumDeclaration);
		CounterMap counters = new CounterMap(enumConstantDeclarations.length);
		for (EnumConstantDeclaration enumConstantDeclaration : enumConstantDeclarations) {
			String constantName = enumConstantDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(constantName);
			this.enumConstants.add(this.buildEnumConstant(constantName, occurrence, enumConstantDeclaration));
		}
	}

	private void syncEnumConstants(EnumDeclaration enumDeclaration) {
		EnumConstantDeclaration[] enumConstantDeclarations = this.annotatedElement.getEnumConstants(enumDeclaration);
		CounterMap counters = new CounterMap(enumConstantDeclarations.length);
		HashSet<JavaResourceEnumConstant> enumConstantsToRemove = new HashSet<JavaResourceEnumConstant>(this.enumConstants);
		for (EnumConstantDeclaration enumConstantDeclaration : enumConstantDeclarations) {
			String constantName = enumConstantDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(constantName);

			JavaResourceEnumConstant enumConstant = this.getEnumConstant(constantName, occurrence);
			if (enumConstant == null) {
				this.addEnumConstant(this.buildEnumConstant(constantName, occurrence, enumConstantDeclaration));
			} else {
				enumConstantsToRemove.remove(enumConstant);
				enumConstant.synchronizeWith(enumConstantDeclaration);
			}
		}
		this.removeEnumConstants(enumConstantsToRemove);
	}

	private JavaResourceEnumConstant buildEnumConstant(String fieldName, int occurrence, EnumConstantDeclaration enumConstantDeclaration) {
		return SourceEnumConstant.newInstance(this, this.annotatedElement, fieldName, occurrence, this.getJavaResourceCompilationUnit(), enumConstantDeclaration);
	}


	public Iterable<JavaResourceType> getTypes() {
		return EmptyIterable.instance();
	}

	public Iterable<JavaResourceType> getAllTypes() {
		return EmptyIterable.instance();
	}

	public Iterable<JavaResourceEnum> getEnums() {
		return EmptyIterable.instance();
	}

	public Iterable<JavaResourceEnum> getAllEnums() {
		return new SingleElementIterable<JavaResourceEnum>(this);
	}

	// ********** CounterMap **********

	private static class CounterMap {
		private final HashMap<Object, SimpleIntReference> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, SimpleIntReference>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			SimpleIntReference counter = this.counters.get(o);
			if (counter == null) {
				counter = new SimpleIntReference();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.getValue();
		}
	}

}
