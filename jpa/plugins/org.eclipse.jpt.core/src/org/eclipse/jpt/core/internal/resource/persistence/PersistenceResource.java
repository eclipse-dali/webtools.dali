/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.internal.resource.persistence.translators.PersistenceTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;

public class PersistenceResource extends JpaXmlResource
{
	public PersistenceResource(Renderer aRenderer) {
		super(aRenderer);
	}

	public PersistenceResource(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
	}
	
	/**
	 * @see TranslatorResource#getRootTranslator() 
	 */
	public Translator getRootTranslator() {
		return PersistenceTranslator.INSTANCE;
	}
	
	/**
	 * @see JpaXmlResource#handleJavaElementChangedEvent(ElementChangedEvent)
	 */
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		// TODO Auto-generated method stub	
	}
	
	public XmlPersistence getPersistence() {
		return (XmlPersistence) getRootObject();
	}
}
