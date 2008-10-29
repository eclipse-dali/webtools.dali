/*******************************************************************************
 *  Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class SequenceGeneratorTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public SequenceGeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
	}

	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createSequenceNameTranslator(),
			createInitialValueTranslator(),
			createAllocationSizeTranslator(),
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlGenerator_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createSequenceNameTranslator() {
		return new Translator(SEQUENCE_NAME, ORM_PKG.getXmlSequenceGenerator_SequenceName(), DOM_ATTRIBUTE);
	}
	
	private Translator createInitialValueTranslator() {
		return new Translator(INITIAL_VALUE, ORM_PKG.getXmlGenerator_InitialValue(), DOM_ATTRIBUTE);
	}
	
	private Translator createAllocationSizeTranslator() {
		return new Translator(ALLOCATION_SIZE, ORM_PKG.getXmlGenerator_AllocationSize(), DOM_ATTRIBUTE);
	}
}
