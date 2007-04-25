/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class SequenceGeneratorTranslator extends GeneratorTranslator
{
	
	public SequenceGeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, NO_STYLE);
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createSequenceNameTranslator(),
			createInitialValueTranslator(),
			createAllocationSizeTranslator(),
		};
	}

	protected Translator createSequenceNameTranslator() {
		return new Translator(SEQUENCE_GENERATOR__SEQUENCE_NAME, MAPPINGS_PKG.getISequenceGenerator_SpecifiedSequenceName(), DOM_ATTRIBUTE);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
	}
}
