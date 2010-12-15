/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jpt.core.internal.utility.jdt.JDTEnum;
import org.eclipse.jpt.core.utility.jdt.Enum;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.IntReference;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;

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
			EnumDeclaration enumDeclaration,
			CompilationUnit astRoot) {
		Enum _enum = new JDTEnum(
			enumDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceEnum jre = new SourceEnum(javaResourceCompilationUnit, _enum);
		jre.initialize(astRoot);
		return jre;
	}

	/**
	 * build nested type
	 */
	protected static JavaResourceEnum newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			Type declaringType,
			EnumDeclaration enumDeclaration,
			int occurrence,
			CompilationUnit astRoot) {
		Enum _enum = new JDTEnum(
				declaringType,
				enumDeclaration,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceEnum jre = new SourceEnum(javaResourceCompilationUnit, _enum);
		jre.initialize(astRoot);
		return jre;
	}

	private SourceEnum(JavaResourceCompilationUnit javaResourceCompilationUnit, Enum _enum) {
		super(javaResourceCompilationUnit, _enum);
		this.enumConstants = new Vector<JavaResourceEnumConstant>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.initializeEnumConstants(astRoot);
	}


	// ********** update **********

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncEnumConstants(astRoot);
	}


	// ********** SourceAnnotatedElement implementation **********

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);

		for (JavaResourceEnumConstant enumConstant : this.getEnumConstants()) {
			enumConstant.resolveTypes(astRoot);
		}
	}


	// ******** JavaResourceEnum implementation ********

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

	private void initializeEnumConstants(CompilationUnit astRoot) {
		EnumConstantDeclaration[] enumConstantDeclarations = this.annotatedElement.getEnumConstants(astRoot);
		CounterMap counters = new CounterMap(enumConstantDeclarations.length);
		for (EnumConstantDeclaration enumConstantDeclaration : enumConstantDeclarations) {
			String constantName = enumConstantDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(constantName);
			this.enumConstants.add(this.buildEnumConstant(constantName, occurrence, astRoot));
		}
	}

	private void syncEnumConstants(CompilationUnit astRoot) {
		EnumConstantDeclaration[] enumConstantDeclarations = this.annotatedElement.getEnumConstants(astRoot);
		CounterMap counters = new CounterMap(enumConstantDeclarations.length);
		HashSet<JavaResourceEnumConstant> enumConstantsToRemove = new HashSet<JavaResourceEnumConstant>(this.enumConstants);
		for (EnumConstantDeclaration enumConstantDeclaration : enumConstantDeclarations) {
			String constantName = enumConstantDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(constantName);

			JavaResourceEnumConstant enumConstant = this.getEnumConstant(constantName, occurrence);
			if (enumConstant == null) {
				this.addEnumConstant(this.buildEnumConstant(constantName, occurrence, astRoot));
			} else {
				enumConstantsToRemove.remove(enumConstant);
				enumConstant.synchronizeWith(astRoot);
			}
		}
		this.removeEnumConstants(enumConstantsToRemove);
	}

	private JavaResourceEnumConstant buildEnumConstant(String fieldName, int occurrence, CompilationUnit astRoot) {
		return SourceEnumConstant.newInstance(this, this.annotatedElement, fieldName, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
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
		private final HashMap<Object, IntReference> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, IntReference>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			IntReference counter = this.counters.get(o);
			if (counter == null) {
				counter = new IntReference();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.getValue();
		}
	}

}
