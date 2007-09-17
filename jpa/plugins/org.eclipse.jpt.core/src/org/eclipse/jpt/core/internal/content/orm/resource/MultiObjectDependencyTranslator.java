/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.DependencyTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.w3c.dom.Node;

public abstract class MultiObjectDependencyTranslator extends DependencyTranslator
{
	private static final Translator[] EMPTY_TRANSLATORS = new Translator[]{};
	
	
	private Map<EObject, Translator> translatorMap;
	
	public MultiObjectDependencyTranslator(String domNameAndPath, EStructuralFeature aFeature, EStructuralFeature aDependentFeature) {
		super(domNameAndPath, aFeature, aDependentFeature);
		this.translatorMap = new HashMap<EObject, Translator>();
	}
	
	
	public Translator getDelegateFor(EObject o) {
		return translatorMap.get(o);
	}
	
	public void setDelegateFor(EObject o, Translator t) {
		translatorMap.put(o, t);
	}
	
	public abstract Translator getDelegateFor(String domName, String readAheadName);
	
	public EObject createEMFObject(String nodeName, String readAheadName) {
		Translator translator = getDelegateFor(nodeName, readAheadName);
		EObject eObject = translator.createEMFObject(nodeName, readAheadName);
		this.translatorMap.put(eObject, translator);
		return eObject;
	}
	
	public Translator[] getChildren(Object o, int version) {
		if (o == null) {
			return EMPTY_TRANSLATORS;
		}
		Translator[] children = getDelegateFor((EObject) o).getChildren(o, version);
		if (children == null) {
			return EMPTY_TRANSLATORS;
		}
		return children;
	}
	
	public String getDOMName(Object value) {
		return getDelegateFor((EObject) value).getDOMName(value);
	}
	
	public boolean isManagedByParent() {
		return false;
	}
	
	@Override
	public boolean shouldIndentEndTag(Node node) {
		if (node.getNodeName().equals(getDOMPath())) {
			return super.shouldIndentEndTag(node);
		}
		Translator delegate = getDelegateFor(node.getNodeName(), null);
		if (delegate != null) {
			return delegate.shouldIndentEndTag(node);
		}
		return super.shouldIndentEndTag(node);
	}

}
