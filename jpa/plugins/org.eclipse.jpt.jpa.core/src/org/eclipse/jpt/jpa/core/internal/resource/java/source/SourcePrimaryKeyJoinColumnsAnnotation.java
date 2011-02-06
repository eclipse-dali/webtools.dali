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
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestablePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnsAnnotation;

/**
 * <code>javax.persistence.PrimaryKeyJoinColumns</code>
 */
public final class SourcePrimaryKeyJoinColumnsAnnotation
	extends SourceAnnotation<Member>
	implements PrimaryKeyJoinColumnsAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final Vector<NestablePrimaryKeyJoinColumnAnnotation> pkJoinColumns = new Vector<NestablePrimaryKeyJoinColumnAnnotation>();


	public SourcePrimaryKeyJoinColumnsAnnotation(JavaResourceNode parent, Member member) {
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
				this.pkJoinColumns.isEmpty();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.pkJoinColumns);
	}


	// ********** AnnotationContainer Implementation **********

	public String getElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMNS__VALUE;
	}

	public String getNestedAnnotationName() {
		return PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME;
	}

	public Iterable<NestablePrimaryKeyJoinColumnAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<NestablePrimaryKeyJoinColumnAnnotation>(this.pkJoinColumns);
	}

	public int getNestedAnnotationsSize() {
		return this.pkJoinColumns.size();
	}

	public void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation) {
		this.nestStandAloneAnnotation(standAloneAnnotation, this.pkJoinColumns.size());
	}

	private void nestStandAloneAnnotation(NestableAnnotation standAloneAnnotation, int index) {
		standAloneAnnotation.convertToNested(this, this.daa, index);
	}

	public void addNestedAnnotation(int index, NestableAnnotation annotation) {
		this.pkJoinColumns.add(index, (NestablePrimaryKeyJoinColumnAnnotation) annotation);
	}

	public void convertLastNestedAnnotationToStandAlone() {
		this.pkJoinColumns.remove(0).convertToStandAlone();
	}

	public NestablePrimaryKeyJoinColumnAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.pkJoinColumns.size());
	}

	private NestablePrimaryKeyJoinColumnAnnotation addNestedAnnotation(int index) {
		NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn = this.buildPrimaryKeyJoinColumn(index);
		this.pkJoinColumns.add(index, pkJoinColumn);
		return pkJoinColumn;
	}

	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.pkJoinColumns.size();
		NestablePrimaryKeyJoinColumnAnnotation pkJoinColumn = this.addNestedAnnotation(index);
		pkJoinColumn.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(PK_JOIN_COLUMNS_LIST, index, pkJoinColumn);
	}

	private NestablePrimaryKeyJoinColumnAnnotation buildPrimaryKeyJoinColumn(int index) {
		// pass the Java resource persistent member as the nested annotation's parent
		// since the nested annotation can be converted to stand-alone
		return SourcePrimaryKeyJoinColumnAnnotation.createNestedPrimaryKeyJoinColumn(this.parent, this.annotatedElement, index, this.daa);
	}

	public NestablePrimaryKeyJoinColumnAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.pkJoinColumns, targetIndex, sourceIndex).get(targetIndex);
	}

	public NestablePrimaryKeyJoinColumnAnnotation removeNestedAnnotation(int index) {
		return this.pkJoinColumns.remove(index);
	}

	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.pkJoinColumns, PK_JOIN_COLUMNS_LIST);
	}
}
