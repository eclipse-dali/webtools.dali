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

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;

/**
 * <code>
 * <ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.DiscriminatorColumn
 * <li>javax.persistence.PrimaryKeyJoinColumn
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * <li>javax.persistence.OrderColumn
 * </ul>
 * </code>
 */
public abstract class SourceNamedColumnAnnotation
	extends SourceAnnotation<Member>
	implements NamedColumnAnnotation
{
	private DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private DeclarationAnnotationElementAdapter<String> columnDefinitionDeclarationAdapter;
	private AnnotationElementAdapter<String> columnDefinitionAdapter;
	private String columnDefinition;


	protected SourceNamedColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}
	
	protected SourceNamedColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.columnDefinitionDeclarationAdapter = this.buildColumnDefinitionDeclarationAdapter();
		this.columnDefinitionAdapter = this.buildColumnDefinitionAdapter();
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.columnDefinition = this.buildColumnDefinition(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncColumnDefinition(this.buildColumnDefinition(astRoot));
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

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
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

	private String buildColumnDefinition(CompilationUnit astRoot) {
		return this.columnDefinitionAdapter.getValue(astRoot);
	}

	public TextRange getColumnDefinitionTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.columnDefinitionDeclarationAdapter, astRoot);
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
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.columnDefinitionDeclarationAdapter = this.buildColumnDefinitionDeclarationAdapter();
		this.columnDefinitionAdapter = this.buildColumnDefinitionAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(NAME_PROPERTY, this.name);
		this.name = null;
		map.put(COLUMN_DEFINITION_PROPERTY, this.columnDefinition);
		this.columnDefinition = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setName((String) map.get(NAME_PROPERTY));
		this.setColumnDefinition((String) map.get(COLUMN_DEFINITION_PROPERTY));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
