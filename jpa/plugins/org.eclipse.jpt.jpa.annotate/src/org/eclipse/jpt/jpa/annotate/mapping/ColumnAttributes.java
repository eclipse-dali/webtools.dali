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

import java.text.NumberFormat;
import java.text.ParseException;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.annotate.internal.plugin.JptJpaAnnotatePlugin;

public class ColumnAttributes extends AnnotationAttributes
{	
	public ColumnAttributes()
	{
		super();
	}
	
	public ColumnAttributes(ColumnAttributes another)
	{
		super(another);
	}
	
	public void setName(String name)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.NAME, name);
		setAnnotationAttribute(attr);
	}
	
	public String getName()
	{
		String name = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.NAME);
		if (attr != null)
			name = attr.attrValue;
		return name;
	}
	
	public void setReferencedColumnName(String referencedColumnName)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.REFERENCED_COLUMN_NAME, 
				referencedColumnName);
		setAnnotationAttribute(attr);		
	}
	
	public String getReferencedColumnName()
	{
		String refColName = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.REFERENCED_COLUMN_NAME);
		if (attr != null)
			refColName = attr.attrValue;
		return refColName;
	}
	
	public void setUnique(boolean unique)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.UNIQUE, 
				unique ? "true" : "false");
		setAnnotationAttribute(attr);		
	}
	
	public boolean isSetUnique()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.UNIQUE) != null;
	}
	
	public boolean isUnique()
	{
		boolean isUnique = false;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.UNIQUE);
		if (attr != null)
		{
			isUnique = attr.attrValue.equals("true") ? true : false;
		}
		return isUnique;
	}
	
	public void setNullable(boolean nullable)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.NULLABLE, 
				nullable ? "true" : "false");
		setAnnotationAttribute(attr);		
	}
	
	public boolean isSetNullable()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.NULLABLE) != null;
	}
	
	public boolean isNullable()
	{
		boolean isNullable = false;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.NULLABLE);
		if (attr != null)
		{
			isNullable = attr.attrValue.equals("true") ? true : false;
		}
		return isNullable;
	}
	
	public void setInsertable(boolean insertable)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.INSERTABLE, 
				insertable ? "true" : "false");
		setAnnotationAttribute(attr);		
	}

	public boolean isSetInsertable()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.INSERTABLE) != null;	
	}
	
	public boolean isInsertable()
	{
		boolean isInsertable = false;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.INSERTABLE);
		if (attr != null)
		{
			isInsertable = attr.attrValue.equals("true") ? true : false;
		}
		return isInsertable;
	}

	public void setUpdatable(boolean updatable)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.UPDATABLE, 
				updatable ? "true" : "false");
		setAnnotationAttribute(attr);		
	}

	public boolean isSetUpdatable()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.UPDATABLE) != null;
	}
	
	public boolean isUpdatable()
	{
		boolean isUpdatable = false;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.UPDATABLE);
		if (attr != null)
		{
			isUpdatable = attr.attrValue.equals("true") ? true : false;
		}
		return isUpdatable;
	}
	
	public void setColumnDefinition(String columnDefinition)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.COLUMN_DEFINITION, 
				columnDefinition);
		setAnnotationAttribute(attr);		
	}
	
	public String getColumnDefinition()
	{
		String colDef = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.COLUMN_DEFINITION);
		if (attr != null)
			colDef = attr.attrValue;
		return colDef;
	}
	
	public void setTable(String table)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.TABLE, table);
		setAnnotationAttribute(attr);
	}
	
	public String getTable()
	{
		String table = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.TABLE);
		if (attr != null)
			table = attr.attrValue;
		return table;
	}

	public void setLength(int length)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.LENGTH, 
				String.valueOf(length));
		setAnnotationAttribute(attr);		
	}
	
	public void setLength(String lengthStr)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.LENGTH, 
				lengthStr);
		setAnnotationAttribute(attr);
	}

	public boolean isSetLength()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.LENGTH) == null ? 
				false : 
				!StringTools.equalsIgnoreCase
						(
						getAnnotationAttribute(AnnotationAttributeNames.LENGTH).attrValue, 
						"-1" //$NON-NLS-1$
						);
	}
	
	public int getLength()
	{
		int length = 0;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.LENGTH);
		if (attr != null)
		{
			try 
			{
				Number number = NumberFormat.getNumberInstance().parse(attr.attrValue);
				length = number.intValue();
			}
			catch (ParseException pe)
			{
				JptJpaAnnotatePlugin.instance().logError(pe);
			}
		}
		return length;
	}

	public void setPrecision(int precision)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.PRECISION, 
				String.valueOf(precision));
		setAnnotationAttribute(attr);		
	}

	public void setPrecision(String precisionStr)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.PRECISION, 
				precisionStr);
		setAnnotationAttribute(attr);
	}

	public boolean isSetPrecision()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.PRECISION) != null;
	}
	
	public int getPrecision()
	{
		int precision = 0;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.PRECISION);
		if (attr != null)
		{
			try 
			{
				Number number = NumberFormat.getNumberInstance().parse(attr.attrValue);
				precision = number.intValue();
			}
			catch (ParseException pe)
			{
				JptJpaAnnotatePlugin.instance().logError(pe);
			}
		}
		return precision;
	}

	public void setScale(int scale)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.SCALE, 
				String.valueOf(scale));
		setAnnotationAttribute(attr);				
	}

	public void setScale(String scaleStr)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.SCALE, 
				scaleStr);
		setAnnotationAttribute(attr);
	}
	
	public boolean isSetScale()
	{
		return getAnnotationAttribute(AnnotationAttributeNames.SCALE) != null;
	}
	
	public int getScale()
	{
		int scale = 0;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.SCALE);
		if (attr != null)
		{
			try 
			{
				Number number = NumberFormat.getNumberInstance().parse(attr.attrValue);
				scale = number.intValue();
			}
			catch (ParseException pe)
			{
				JptJpaAnnotatePlugin.instance().logError(pe);
			}
		}
		return scale;
	}	
}
