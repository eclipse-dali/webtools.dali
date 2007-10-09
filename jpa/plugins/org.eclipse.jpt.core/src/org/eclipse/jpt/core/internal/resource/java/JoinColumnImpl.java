/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public class JoinColumnImpl extends AbstractColumnImpl implements JoinColumn
{

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;

	private String referencedColumnName;
	
	public JoinColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa);
		//this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, daa);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}
	
	@Override
	protected String nameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected String tableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}

	public String getAnnotationName() {
		return JPA.JOIN_COLUMN;
	}

	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}
	
	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
		this.referencedColumnNameAdapter.setValue(referencedColumnName);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setReferencedColumnName(this.referencedColumnNameAdapter.getValue(astRoot));
	}

}
