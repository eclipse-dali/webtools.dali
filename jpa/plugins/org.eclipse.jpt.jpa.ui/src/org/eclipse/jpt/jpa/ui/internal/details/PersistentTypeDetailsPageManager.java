/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

public class PersistentTypeDetailsPageManager
	extends AbstractJpaDetailsPageManager<PersistentType>
{
	private final HashMap<String, JpaComposite> mappingComposites = new HashMap<String, JpaComposite>();
	private PageBook mappingPageBook;
	private PropertyValueModel<TypeMapping> mappingHolder;


	public PersistentTypeDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		new PersistentTypeMapAsComposite(this, container);
		this.mappingPageBook = this.buildMappingPageBook(container);
	}

	protected PageBook buildMappingPageBook(Composite parent) {
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
	
	private Transformer<TypeMapping, Control> buildPaneTransformer() {
		return new Transformer<TypeMapping, Control>() {
			public Control transform(TypeMapping typeMapping) {
				if (typeMapping == null) {
					return null;
				}
				return getMappingComposite(typeMapping.getKey()).getControl();
			}
		};
	}
	
	protected PropertyValueModel<TypeMapping> buildMappingHolder(String key) {
		return new FilteringPropertyValueModel<TypeMapping>(
			this.mappingHolder,
			buildMappingFilter(key)
		);
	}

	private PropertyAspectAdapter<PersistentType, TypeMapping> buildMappingHolder() {
		return new PropertyAspectAdapter<PersistentType, TypeMapping>(getSubjectHolder(), PersistentType.MAPPING_PROPERTY) {
			@Override
			protected TypeMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}

	private Filter<TypeMapping> buildMappingFilter(final String key) {
		return new Filter<TypeMapping>() {
			public boolean accept(TypeMapping mapping) {
				return (mapping == null || key == null) || key.equals(mapping.getKey());
			}
		};
	}

	
	/* CU private */ JpaComposite getMappingComposite(String key) {
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
		return (ui == null) ? null : ui.buildTypeMappingComposite(
				this.getSubject().getResourceType(), 
				key, 
				this.buildMappingHolder(key), 
				this.getMappingCompositeEnabledModel(), 
				pageBook, 
				this.getWidgetFactory(),
				this.getResourceManager()
			);
	}

	private static final PropertyValueModel<Boolean> TRUE_ENABLED_MODEL = new StaticPropertyValueModel<Boolean>(Boolean.TRUE);

	protected PropertyValueModel<Boolean> getMappingCompositeEnabledModel() {
		return TRUE_ENABLED_MODEL;		
	}

	@Override
	public void controlDisposed() {
		JptJpaUiPlugin.instance().trace(TRACE_OPTION, "dispose"); //$NON-NLS-1$

		this.mappingComposites.clear();
		super.controlDisposed();
	}

	private static final String TRACE_OPTION = PersistentTypeDetailsPageManager.class.getSimpleName();
}
