/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PrimaryKeyJoinColumnTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public PrimaryKeyJoinColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createReferencedColumnNameTranslator(),
			createColumnDefinitionTranslator(),
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getPrimaryKeyJoinColumn_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createReferencedColumnNameTranslator() {
		return new Translator(REFERENCED_COLUMN_NAME, ORM_PKG.getPrimaryKeyJoinColumn_ReferencedColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnDefinitionTranslator() {
		return new Translator(COLUMN_DEFINITION, ORM_PKG.getPrimaryKeyJoinColumn_ColumnDefinition(), DOM_ATTRIBUTE);
	}
}
