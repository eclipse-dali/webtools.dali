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
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;

public class ColumnImpl extends AbstractAnnotationResource<Attribute> implements Column
{
	private final AnnotationElementAdapter<String> nameAdapter;
	private final AnnotationElementAdapter<String> tableAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private static final DeclarationAnnotationElementAdapter<String> TABLE_ADAPTER = buildTableAdapter();

	private String name;
	private String table;

	public ColumnImpl(JavaResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), NAME_ADAPTER);
		this.tableAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), TABLE_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.COLUMN;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.nameAdapter.setValue(name);
	}
	
	public String getTable() {
		return this.table;
	}
	
	public void setTable(String table) {
		this.table = table;
		this.tableAdapter.setValue(table);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		setName(this.nameAdapter.getValue(astRoot));
		setTable(this.tableAdapter.getValue(astRoot));
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.COLUMN__NAME);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildTableAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.COLUMN__TABLE);
	}

}
