/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.OneToManyTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.EmptyTagBooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkOneToManyTranslator extends OneToManyTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkOneToManyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTranslator(),
			createMappedByTranslator(),
			createOrderByTranslator(),
			createMapKeyTranslator(),
			createJoinTableTranslator(),
			createJoinColumnTranslator(),
			createCascadeTranslator(),
			createPrivateOwnedTranslator(),
			createJoinFetchTranslator(),
			createPropertyTranslator(),
			createAccessMethodsTranslator()
		};
	}
	
	protected Translator createPrivateOwnedTranslator() {
		return new EmptyTagBooleanTranslator(PRIVATE_OWNED, ECLIPSELINK_ORM_PKG.getXmlPrivateOwned_PrivateOwned());
	}
	
	protected Translator createJoinFetchTranslator() {
		return new Translator(JOIN_FETCH, ECLIPSELINK_ORM_PKG.getXmlJoinFetch_JoinFetch());
	}
	
	protected Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, ECLIPSELINK_ORM_PKG.getXmlOneToMany_Properties());
	}

	protected Translator createAccessMethodsTranslator() {
		return new AccessMethodsTranslator();
	}
}
