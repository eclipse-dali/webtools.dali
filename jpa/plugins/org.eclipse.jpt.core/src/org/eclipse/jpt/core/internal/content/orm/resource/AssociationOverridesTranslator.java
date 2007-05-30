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
import org.eclipse.jpt.core.internal.content.orm.resource.AssociationOverrideTranslator.AssociationOverrideBuilder;
import org.eclipse.wst.common.internal.emf.resource.MultiObjectTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Created this class so there would be a 1-1 association between 
 * AssociationOverride and AssociationOverrideTranslator.  Without this the state
 * stored on AssociationOverrideTranslator will be invalid for multiple AssociationOverrides.
 * see bug 188901.
 * 
 * TODO 189767 - memory leak if an associationOverride is removed from the model, it is still
 * stored along with its translator in the translator map
 */
public class AssociationOverridesTranslator extends MultiObjectTranslator implements OrmXmlMapper
{
	private AssociationOverrideBuilder associationOverrideBuilder;

	private Map<EObject, AssociationOverrideTranslator> translatorMap;
	
	public AssociationOverridesTranslator(String domNameAndPath, EStructuralFeature aFeature, AssociationOverrideBuilder associationOverrideBuilder) {
		super(domNameAndPath, aFeature);
		this.associationOverrideBuilder = associationOverrideBuilder;
		this.translatorMap = new HashMap<EObject, AssociationOverrideTranslator>();
	}
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		AssociationOverrideTranslator translator = (AssociationOverrideTranslator) getDelegateFor(nodeName, readAheadName);
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE :
				return new AssociationOverrideTranslator(this.domNameAndPath, getFeature(), this.associationOverrideBuilder);
		}
		
		return null;
	}
	
	@Override
	public Translator getDelegateFor(String domName, String readAheadName) {
		if (domName.equals(ENTITY__ASSOCIATION_OVERRIDE)) {
			return new AssociationOverrideTranslator(this.domNameAndPath, getFeature(), this.associationOverrideBuilder);
		}
		throw new IllegalStateException("Illegal dom name: " + domName); //$NON-NLS-1$
	}

}
