/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.HashMap;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

public abstract class PersistentAttributeDetailsPageManager<A extends ReadOnlyPersistentAttribute>
	extends AbstractJpaDetailsPageManager<A>
{
	private final HashMap<String, JpaComposite> mappingComposites = new HashMap<String, JpaComposite>();
	private PageBook mappingPageBook;

	private PropertyValueModel<AttributeMapping> mappingHolder;
	
	protected PersistentAttributeDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.mappingPageBook = this.buildMappingPageBook(container);
	}

	private PageBook buildMappingPageBook(Composite parent) {
		PageBook book = new PageBook(parent, SWT.NONE);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.verticalIndent = 5;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		book.setLayoutData(gridData);
		
		this.mappingHolder = this.buildMappingHolder();
		new ControlSwitcher(this.mappingHolder, this.buildPaneTransformer(), book);

		return book;
	}

	private Transformer<AttributeMapping, Control> buildPaneTransformer() {
		return new Transformer<AttributeMapping, Control>() {
			public Control transform(AttributeMapping attributeMapping) {
				return (attributeMapping == null) ? null : getMappingComposite(attributeMapping.getKey()).getControl();
			}
		};
	}

	protected JpaComposite getMappingComposite(String key) {
		JpaComposite composite = this.mappingComposites.get(key);
		if (composite == null) {
			composite = this.buildMappingComposite(this.mappingPageBook, key);
			if (composite != null) {
				this.mappingComposites.put(key, composite);
			}
		}
		return composite;
	}

	protected JpaComposite buildMappingComposite(PageBook pageBook, String key) {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.buildAttributeMappingComposite(
				this.getSubject().getResourceType(),
				key,
				pageBook,
				this.buildMappingHolder(key),
				this.getMappingCompositeEnabledModel(),
				this.getWidgetFactory(),
				this.getResourceManager()
			);
	}

	protected abstract PropertyValueModel<Boolean> getMappingCompositeEnabledModel();

	private PropertyValueModel<AttributeMapping> buildMappingHolder(final String key) {
		return new FilteringPropertyValueModel<AttributeMapping>(
			this.mappingHolder,
			buildMappingFilter(key)
		);
	}

	private Predicate<AttributeMapping> buildMappingFilter(String mappingKey) {
		return new MappingFilter(mappingKey);
	}


	private ModifiablePropertyValueModel<AttributeMapping> buildMappingHolder() {
		return new PropertyAspectAdapter<A, AttributeMapping>(
			getSubjectHolder(),
			ReadOnlyPersistentAttribute.MAPPING_PROPERTY)
		{
			@Override
			protected AttributeMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}

	@Override
	public void controlDisposed() {
		JptJpaUiPlugin.instance().trace(TRACE_OPTION, "dispose"); //$NON-NLS-1$

		this.mappingComposites.clear();
		super.controlDisposed();
	}

	private static final String TRACE_OPTION = PersistentAttributeDetailsPageManager.class.getSimpleName();

	private class MappingFilter implements Predicate<AttributeMapping> {
		private String mappingKey;

		MappingFilter(String mappingKey) {
			super();
			this.mappingKey = mappingKey;
		}

		public boolean evaluate(AttributeMapping mapping) {
			return (mapping == null || this.mappingKey == null) || this.mappingKey.equals(mapping.getKey());
		}
	}
}
