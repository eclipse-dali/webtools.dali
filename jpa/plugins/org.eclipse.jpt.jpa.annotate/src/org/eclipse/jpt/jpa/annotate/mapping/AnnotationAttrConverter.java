/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

import java.util.HashMap;
import java.util.Map;

public class AnnotationAttrConverter
{
	public final static String FETCH = "fetch";
	public final static String LOB_TYPE = "lob-type";
	public final static String DISCRIMINATOR_TYPE = "discriminator-type";
	public final static String TEMPORAL = "temporal";
	public final static String INHERITANCE_STRAGETY = "inheritance-strategy";
	public final static String GENERATION_STRATEGY = "generation-stragegy";
	
	private static Map<String, String> tagEnumClassMap;
	private static Map<String, String[]> tagEnumValuesMap;
	
	static
	{
		tagEnumClassMap = new HashMap<String, String>(8);
		/*the enumeration tags are:
		 * fetch --> javax.persistence.FetchType
		 * lob --> javax.persistence.Lob
		 * discriminator-type --> javax.persistence.DiscriminatorType
		 * temporal --> javax.persistence.Temporal
		 * inheritance-strategy --> javax.persistence.InheritanceType
		 * strategy --> javax.persistence.GenerationType
		 * */
		tagEnumClassMap.put(FETCH, "javax.persistence.FetchType");
		tagEnumClassMap.put(LOB_TYPE, "java.lang.annotation.ElementType");
		tagEnumClassMap.put(DISCRIMINATOR_TYPE, "javax.persistence.DiscriminatorType");
		tagEnumClassMap.put(TEMPORAL, "javax.persistence.TemporalType");
		tagEnumClassMap.put(INHERITANCE_STRAGETY, "javax.persistence.InheritanceType");
		tagEnumClassMap.put(GENERATION_STRATEGY, "javax.persistence.GenerationType");
		
		tagEnumValuesMap = new HashMap<String, String[]>(8);
		tagEnumValuesMap.put(FETCH, new String[]{"LAZY", "EAGER"});
		tagEnumValuesMap.put(LOB_TYPE, new String[]{"METHOD", "FIELD"});
		tagEnumValuesMap.put(DISCRIMINATOR_TYPE, new String[]{"CHAR", "INTEGER", "STRING"});
		tagEnumValuesMap.put(TEMPORAL, new String[]{"DATE", "TIME", "TIMESTAMP"});
		tagEnumValuesMap.put(INHERITANCE_STRAGETY, new String[]{"JOINED", "SINGLE_TABLE", "TABLE_PER_CLASS"});
		tagEnumValuesMap.put(GENERATION_STRATEGY, new String[]{"AUTO", "IDENTITY", "SEQUENCE", "TABLE"});
	}
	
	public static String[] getTagEnumStringValues(String tagName)
	{
		return tagEnumValuesMap.get(tagName);
	}
	
	public static String getTagEnumClass(String tagName)
	{
		return tagEnumClassMap.get(tagName);
	}
	
	public static String adjustAnnotationValue(AnnotationAttribute attr)
	{
//		assert attr.dataType == AttributeDataTypes.STRING_TYPE;
		String attrVal = attr.attrValue;
		String enumClass = getTagEnumClass(attr.tagName);
		if (enumClass == null)
			return attrVal;
		if (enumClass.equals("javax.persistence.DiscriminatorType") //$NON-NLS-1$
				&& "CHARACTER".equals(attrVal))  //$NON-NLS-1$
		{
				attrVal = "CHAR"; //$NON-NLS-1$
		}
		return enumClass + '.' + attrVal;
	}
}
