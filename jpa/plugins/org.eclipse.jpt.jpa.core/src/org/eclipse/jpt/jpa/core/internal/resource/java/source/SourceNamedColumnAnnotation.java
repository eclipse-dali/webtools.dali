/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.DiscriminatorColumn
 * <li>javax.persistence.PrimaryKeyJoinColumn
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * <li>javax.persistence.OrderColumn
 * </ul></code>
 */
public abstract class SourceNamedColumnAnnotation
	extends SourceAnnotation
	implements NamedColumnAnnotation
{
	private DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;

	private DeclarationAnnotationElementAdapter<String> columnDefinitionDeclarationAdapter;
	private AnnotationElementAdapter<String> columnDefinitionAdapter;
	private String columnDefinition;
	private TextRange columnDefinitionTextRange;


	protected SourceNamedColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}
	
	protected SourceNamedColumnAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.columnDefinitionDeclarationAdapter = this.buildColumnDefinitionDeclarationAdapter();
		this.columnDefinitionAdapter = this.buildColumnDefinitionAdapter();
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.columnDefinition = this.buildColumnDefinition(astAnnotation);
		this.columnDefinitionTextRange = this.buildColumnDefinitionTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.syncColumnDefinition(this.buildColumnDefinition(astAnnotation));
		this.columnDefinitionTextRange = this.buildColumnDefinitionTextRange(astAnnotation);
	}


	// ********** NamedColumn implementation **********

	public boolean isSpecified() {
		return true;
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}

	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return this.buildStringElementAdapter(this.getNameElementName());
	}

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildStringElementAdapter(this.nameDeclarationAdapter);
	}

	protected abstract String getNameElementName();

	// ***** column definition
	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (this.attributeValueHasChanged(this.columnDefinition, columnDefinition)) {
			this.columnDefinition = columnDefinition;
			this.columnDefinitionAdapter.setValue(columnDefinition);
		}
	}

	private void syncColumnDefinition(String astColumnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = astColumnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, astColumnDefinition);
	}

	private String buildColumnDefinition(Annotation astAnnotation) {
		return this.columnDefinitionAdapter.getValue(astAnnotation);
	}

	public TextRange getColumnDefinitionTextRange() {
		return this.columnDefinitionTextRange;
	}

	private TextRange buildColumnDefinitionTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.columnDefinitionDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildColumnDefinitionDeclarationAdapter() {
		return this.buildStringElementAdapter(this.getColumnDefinitionElementName());
	}

	private AnnotationElementAdapter<String> buildColumnDefinitionAdapter() {
		return this.buildStringElementAdapter(this.columnDefinitionDeclarationAdapter);
	}

	protected abstract String getColumnDefinitionElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.columnDefinition == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
