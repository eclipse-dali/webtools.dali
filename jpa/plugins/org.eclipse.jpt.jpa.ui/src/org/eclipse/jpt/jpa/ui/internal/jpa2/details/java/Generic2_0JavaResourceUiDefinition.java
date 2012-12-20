/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.java;

import java.util.List;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.BasicMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedIdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.OneToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.OneToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.VersionMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.java.AbstractJavaResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.java.DefaultBasicMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.java.DefaultEmbeddedMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.java.NullJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.ElementCollectionMapping2_0UiDefinition;

public class Generic2_0JavaResourceUiDefinition
	extends AbstractJavaResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new Generic2_0JavaResourceUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * zero-argument constructor
	 */
	protected Generic2_0JavaResourceUiDefinition() {
		super();
	}
	
	@Override
	protected JpaUiFactory buildUiFactory() {
		return new Generic2_0JavaUiFactory();
	}
	
	@Override
	protected void addSpecifiedAttributeMappingUiDefinitionsTo(List<MappingUiDefinition> definitions) {
		definitions.add(ElementCollectionMapping2_0UiDefinition.instance());
		definitions.add(IdMappingUiDefinition.instance());
		definitions.add(EmbeddedIdMappingUiDefinition.instance());
		definitions.add(BasicMappingUiDefinition.instance());
		definitions.add(VersionMappingUiDefinition.instance());
		definitions.add(ManyToOneMappingUiDefinition.instance());
		definitions.add(OneToManyMappingUiDefinition.instance());
		definitions.add(OneToOneMappingUiDefinition.instance());
		definitions.add(ManyToManyMappingUiDefinition.instance());
		definitions.add(EmbeddedMappingUiDefinition.instance());
		definitions.add(TransientMappingUiDefinition.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiDefinitionsTo(List<DefaultMappingUiDefinition> definitions) {
		definitions.add(DefaultBasicMappingUiDefinition.instance());
		definitions.add(DefaultEmbeddedMappingUiDefinition.instance());
		definitions.add(NullJavaAttributeMappingUiDefinition.instance());
	}
}
