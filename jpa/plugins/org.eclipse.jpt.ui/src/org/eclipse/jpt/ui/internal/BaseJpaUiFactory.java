/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.java.details.JavaEntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.BasicMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddableComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedIdMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToOneMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedSuperclassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TransientMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.VersionMappingComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmEntityComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitConnectionComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitGeneralComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitPropertiesComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the UI factory required to show the information
 * related to a JPA mapping (type or attribute).
 *
 * @see JpaUiFactory
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class BaseJpaUiFactory implements JpaUiFactory
{
	public JpaComposite<BasicMapping> createBasicMappingComposite(
		PropertyValueModel<BasicMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new BasicMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<Embeddable> createEmbeddableComposite(
		PropertyValueModel<Embeddable> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EmbeddableComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<EmbeddedIdMapping> createEmbeddedIdMappingComposite(
		PropertyValueModel<EmbeddedIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<EmbeddedMapping> createEmbeddedMappingComposite(
		PropertyValueModel<EmbeddedMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EmbeddedMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<JavaEntity> createJavaEntityComposite(
		PropertyValueModel<JavaEntity> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new JavaEntityComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<OrmEntity> createOrmEntityComposite(
		PropertyValueModel<OrmEntity> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new OrmEntityComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<IdMapping> createIdMappingComposite(
		PropertyValueModel<IdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new IdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<ManyToManyMapping> createManyToManyMappingComposite(
		PropertyValueModel<ManyToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new ManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<ManyToOneMapping> createManyToOneMappingComposite(
		PropertyValueModel<ManyToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new ManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<MappedSuperclass> createMappedSuperclassComposite(
		PropertyValueModel<MappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new MappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<OneToManyMapping> createOneToManyMappingComposite(
		PropertyValueModel<OneToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new OneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<OneToOneMapping> createOneToOneMappingComposite(
		PropertyValueModel<OneToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new OneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public ListIterator<JpaPageComposite<PersistenceUnit>> createPersistenceUnitComposites(
		PropertyValueModel<PersistenceUnit> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		ArrayList<JpaPageComposite<PersistenceUnit>> pages =
			new ArrayList<JpaPageComposite<PersistenceUnit>>(1);

		pages.add(new PersistenceUnitGeneralComposite(subjectHolder, parent, widgetFactory));
		pages.add(new PersistenceUnitConnectionComposite(subjectHolder, parent, widgetFactory));
		pages.add(new PersistenceUnitPropertiesComposite(subjectHolder, parent, widgetFactory));

		return pages.listIterator();
	}

	public JpaComposite<TransientMapping> createTransientMappingComposite(
		PropertyValueModel<TransientMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new TransientMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite<VersionMapping> createVersionMappingComposite(
		PropertyValueModel<VersionMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new VersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}