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

public class GeneratedValueAttributes extends AnnotationAttributes
{
	public GeneratedValueAttributes()
	{
		super();
	}
	
	public GeneratedValueAttributes(GeneratedValueAttributes another)
	{
		super(another);
	}
	
	public void setGenerator(String generator)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.GENERATOR, generator);
		setAnnotationAttribute(attr);
	}
	
	public String getGenerator()
	{
		String generator = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.GENERATOR);
		if (attr != null)
			generator = attr.attrValue;
		return generator;
	}
	
	public void setStrategy(String strategy)
	{
		AnnotationAttribute attr = new AnnotationAttribute(
				AnnotationAttributeNames.STRATEGY, AnnotationAttrConverter.GENERATION_STRATEGY, strategy); 
		setAnnotationAttribute(attr);		
	}
	
	public String getStrategy()
	{
		String strategy = null;
		AnnotationAttribute attr = getAnnotationAttribute(AnnotationAttributeNames.STRATEGY);
		if (attr != null)
			strategy = attr.attrValue;
		return strategy;
	}
	
	
}
