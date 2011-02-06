/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableSecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTablesAnnotation;

/**
 * <code>javax.persistence.SecondaryTables</code>
 */
public final class SourceSecondaryTablesAnnotation
	extends SourceAnnotation<Member>
	implements SecondaryTablesAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestableSecondaryTableAnnotation> secondaryTables = new Vector<NestableSecondaryTableAnnotation>();


	public SourceSecondaryTablesAnnotation(JavaResourcePersistentType parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		AnnotationContainerTools.initialize(this, astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		AnnotationContainerTools.synchronize(this, astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.secondaryTables.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.secondaryTables);
	}


	// ********** AnnotationContainer implementation **********

	public String getElementName() {
		return JPA.SECONDARY_TABLES__VALUE;
	}

	public String getNestedAnnotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestableSecondaryTableAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestableSecondaryTableAnnotation>(this.secondaryTables);
	}

	public int getNestedAnnotationsSize() {
		return this.secondaryTables.size();
	}

	public void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.nestStandAloneAnnotation(standAloneAnnotation, this.secondaryTables.size());
	}

	private void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation, int index) {
		standAloneAnnotation.convertToNested(this, this.daa, index);
	}

	public void addNestedAnnotation(int index, NestableAnnotation annotation) {
		this.secondaryTables.add(index, (NestableSecondaryTableAnnotation) annotation);
	}

	public void convertLastNestedAnnotationToStandAlone() {
		this.secondaryTables.remove(0).convertToStandAlone();
	}

	public NestableSecondaryTableAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.secondaryTables.size());
	}

	private NestableSecondaryTableAnnotation addNestedAnnotation(int index) {
		NestableSecondaryTableAnnotation secondaryTable = this.buildSecondaryTable(index);
		this.secondaryTables.add(index, secondaryTable);
		return secondaryTable;
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.secondaryTables.size();
		NestableSecondaryTableAnnotation secondaryTable = this.addNestedAnnotation(index);
		secondaryTable.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(SECONDARY_TABLES_LIST, index, secondaryTable);
	}

	private NestableSecondaryTableAnnotation buildSecondaryTable(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourceSecondaryTableAnnotation.createNestedSecondaryTable(this.parent, this.annotatedElement, index, this.daa);
	}

	public NestableSecondaryTableAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.secondaryTables, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestableSecondaryTableAnnotation removeNestedAnnotation(int index) {
		return this.secondaryTables.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.secondaryTables, SECONDARY_TABLES_LIST);
	}
}
