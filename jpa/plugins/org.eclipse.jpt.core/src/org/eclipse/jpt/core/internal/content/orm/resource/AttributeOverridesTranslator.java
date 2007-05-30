/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.resource.AttributeOverrideTranslator.AttributeOverrideBuilder;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Created this class so there would be a 1-1 association between 
 * AttributeOverride and AttributeOverrideTranslator.  Without this the state
 * stored on AttributeOverrideTranslator will be invalid for multiple AttributeOverrides.
 * see bug 188901.
 * 
 * TODO 189767 - memory leak if an associationOverride is removed from the model, it is still
 * stored along with its translator in the translator map
 */
public class AttributeOverridesTranslator extends MultiObjectTranslator implements OrmXmlMapper
{
	private AttributeOverrideBuilder attributeOverrideBuilder;

	private Map<EObject, AttributeOverrideTranslator> translatorMap;

	public AttributeOverridesTranslator(String domNameAndPath, EStructuralFeature aFeature, AttributeOverrideBuilder attributeOverrideBuilder) {
		super(domNameAndPath, aFeature);
		this.attributeOverrideBuilder = attributeOverrideBuilder;
		this.translatorMap = new HashMap<EObject, AttributeOverrideTranslator>();
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		AttributeOverrideTranslator translator = (AttributeOverrideTranslator) getDelegateFor(nodeName, readAheadName);
		EObject eObject = translator.createEMFObject(nodeName, readAheadName);
		this.translatorMap.put(eObject, translator);
		return eObject;
	}
	
	/* (non-Javadoc)
	 * @see MultiObjectTranslator#getDelegateFor(EObject)
	 */
	@Override
	public Translator getDelegateFor(EObject o) {
		Translator translator = translatorMap.get(o);
		if (translator != null) {
			return translator;
		}
		
		switch (o.eClass().getClassifierID()) {
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE :
				return new AttributeOverrideTranslator(this.domNameAndPath, getFeature(), this.attributeOverrideBuilder);
		}
		
		return null;
	}
	
	@Override
	public Translator getDelegateFor(String domName, String readAheadName) {
		if (domName.equals(ENTITY__ATTRIBUTE_OVERRIDE)) {
			return new AttributeOverrideTranslator(this.domNameAndPath, getFeature(), this.attributeOverrideBuilder);
		}
		throw new IllegalStateException("Illegal dom name: " + domName); //$NON-NLS-1$
	}

}
