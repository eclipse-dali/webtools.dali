/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_2;

import java.util.List;

import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmXmlUiDefinition2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceXmlUiDefinition2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.java.GenericJavaResourceUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm.OrmXmlUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence.PersistenceXmlUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_2.details.orm.OrmXmlUiDefinition2_2;
import org.eclipse.jpt.jpa.ui.internal.jpa2_2.persistence.PersistenceXmlUiDefinition2_2;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceXmlUiDefinition;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformUiProvider2_2 extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new GenericJpaPlatformUiProvider2_2();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJpaPlatformUiProvider2_2() {
		super();
	}
	
	
	// ********** resource ui definitions **********
	
	@Override
	protected void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions) {
		definitions.add(GenericJavaResourceUiDefinition2_1.instance());
		definitions.add(OrmXmlUiDefinition.instance());
		definitions.add(OrmXmlUiDefinition2_0.instance());
		definitions.add(OrmXmlUiDefinition2_1.instance());
		definitions.add(OrmXmlUiDefinition2_2.instance());
		definitions.add(PersistenceXmlUiDefinition.instance());
		definitions.add(PersistenceXmlUiDefinition2_0.instance());
		definitions.add(PersistenceXmlUiDefinition2_1.instance());
		definitions.add(PersistenceXmlUiDefinition2_2.instance());
	}
}
