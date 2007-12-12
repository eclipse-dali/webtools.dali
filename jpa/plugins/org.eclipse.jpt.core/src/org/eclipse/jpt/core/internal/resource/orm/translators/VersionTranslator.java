/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class VersionTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public VersionTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createColumnTranslator(), 
			createTemporalTranslator(),
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, ORM_PKG.getVersion_Column());
	}
	
	private Translator createTemporalTranslator() {
		return new Translator(TEMPORAL, ORM_PKG.getVersion_Temporal());
	}
}
