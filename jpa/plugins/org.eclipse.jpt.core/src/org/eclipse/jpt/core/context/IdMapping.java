/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface IdMapping extends AttributeMapping, ColumnMapping
{
	GeneratedValue getGeneratedValue();
	GeneratedValue addGeneratedValue();
	void removeGeneratedValue();
		String GENERATED_VALUE_PROPERTY = "generatedValueProperty";
	
	TableGenerator getTableGenerator();
	TableGenerator addTableGenerator();
	void removeTableGenerator();
		String TABLE_GENERATOR_PROPERTY = "tableGeneratorProperty";

	SequenceGenerator getSequenceGenerator();
	SequenceGenerator addSequenceGenerator();
	void removeSequenceGenerator();
		String SEQUENCE_GENERATOR_PROPERTY = "sequenceGeneratorProperty";

}
