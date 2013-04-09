/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

public class TableAnnotationAttributes extends AnnotationAttributes
{	
	public TableAnnotationAttributes()
	{	
		super();
	}
	
	public TableAnnotationAttributes(String tableName, String catalog, String schema)
	{
		setTableName(tableName);
		setCatalog(catalog);
		setSchema(schema);
	}

	public TableAnnotationAttributes(TableAnnotationAttributes another)
	{
		super(another);
	}	
	
	public void setTableName(String tableName)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.NAME, tableName);
		setAnnotationAttribute(attr);		
	}
	
	public String getTableName()
	{
		String name = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.NAME);
		if (attr != null)
			name = attr.attrValue;
		return name;
	}
	
	public void setCatalog(String catalog)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.CATALOG, catalog);
		setAnnotationAttribute(attr);
	}
	
	public String getCatalog()
	{
		String catalog = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.CATALOG);
		if (attr != null)
			catalog = attr.attrValue;
		return catalog;
	}
	
	public void setSchema(String schema)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.SCHEMA, schema);
		setAnnotationAttribute(attr);
	}
	
	public String getSchema()
	{
		String schema = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.SCHEMA);
		if (attr != null)
			schema = attr.attrValue;
		return schema;
	}
	
}
