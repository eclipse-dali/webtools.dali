/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1;

import java.util.List;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmXml2_0UiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceXml2_0UiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.java.GenericJavaResourceUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm.OrmXml2_1UiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence.PersistenceXml2_1UiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceXmlUiDefinition;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformUiProvider2_1 extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new GenericJpaPlatformUiProvider2_1();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJpaPlatformUiProvider2_1() {
		super();
	}
	
	
	// ********** resource ui definitions **********
	
	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(GenericJavaResourceUiDefinition2_1.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(OrmXml2_0UiDefinition.instance());
		definitions.add(OrmXml2_1UiDefinition.instance());
		definitions.add(PersistenceXmlUiDefinition.instance());
		definitions.add(PersistenceXml2_0UiDefinition.instance());
		definitions.add(PersistenceXml2_1UiDefinition.instance());
	}
}
