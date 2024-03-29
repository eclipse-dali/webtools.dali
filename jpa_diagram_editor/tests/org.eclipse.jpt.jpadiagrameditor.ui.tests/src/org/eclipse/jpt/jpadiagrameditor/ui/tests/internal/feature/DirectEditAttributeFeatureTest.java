/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.easymock.EasyMock;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DirectEditAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class DirectEditAttributeFeatureTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCheckValueValidStringByJavaConventions() {
		PersistentType jpt = EasyMock.createNiceMock(PersistentType.class);
		PersistentAttribute jpa = configureJpaForJpt(jpt);
		expect(jpt.getAttributeNamed(isA(String.class))).andStubReturn(jpa);
		replay(jpa, jpt);

		IFeatureProvider provider = replayJpaForNullPe(jpa);
		IDirectEditingContext context = replayNullPeContext();

		IDirectEditingFeature feature = new DirectEditAttributeFeature(provider);
		assertNotValidJavaConventionField(feature, context, "");
		assertNotValidJavaConventionField(feature, context, "1a");
		assertNotValidJavaConventionField(feature, context, "d d");
		assertValidField(feature, context, "$d");
		assertNotValidJavaConventionField(feature, context, "enum");
	}

	@Test
	public void testCheckValueValidStringDuplicateAttribute() {
		PersistentAttribute otherJpa = EasyMock.createMock(PersistentAttribute.class);
		PersistentType jpt = EasyMock.createMock(PersistentType.class);
		expect(jpt.getAttributeNamed("attrName")).andStubReturn(otherJpa);

		PersistentAttribute jpa = configureJpaForJpt(jpt);
		replay(jpa, jpt, otherJpa);

		IFeatureProvider provider = replayJpaForNullPe(jpa);
		IDirectEditingContext context = replayNullPeContext();

		IDirectEditingFeature feature = new DirectEditAttributeFeature(provider);

		assertEquals(MessageFormat.format(JPAEditorMessages.DirectEditAttributeFeature_attributeExists, "attrName"), feature.checkValueValid("attrName", context));
	}

	private IFeatureProvider replayJpaForNullPe(PersistentAttribute jpa) {
		IFeatureProvider provider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(provider.getBusinessObjectForPictogramElement(null)).andStubReturn(jpa);
		replay(provider);
		return provider;
	}

	private PersistentAttribute configureJpaForJpt(PersistentType jpt) {
		PersistentAttribute jpa = EasyMock.createMock(PersistentAttribute.class);
		expect(jpa.getParent()).andStubReturn(jpt);
		return jpa;
	}

	private IDirectEditingContext replayNullPeContext() {
		IDirectEditingContext context = EasyMock.createMock(IDirectEditingContext.class);
		expect(context.getPictogramElement()).andStubReturn(null);
		replay(context);
		return context;
	}

	private void assertValidField(IDirectEditingFeature feature, IDirectEditingContext context, String field) {
		assertEquals(null, feature.checkValueValid(field, context));
	}

	private void assertNotValidJavaConventionField(IDirectEditingFeature feature, IDirectEditingContext context,
			String field) {
		final String sourceLevel = "1.5";
		final String complianceLevel = "1.5";
		assertEquals(JavaConventions.validateFieldName(field, sourceLevel, complianceLevel).getMessage(), feature
				.checkValueValid(field, context));
	}

}
