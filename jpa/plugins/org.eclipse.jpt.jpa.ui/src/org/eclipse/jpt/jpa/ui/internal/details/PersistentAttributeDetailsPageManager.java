/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.HashMap;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

public abstract class PersistentAttributeDetailsPageManager<A extends PersistentAttribute>
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
		SWTBindingTools.bind(this.mappingHolder, this.buildPaneTransformer(), book);

		return book;
	}

	private Transformer<AttributeMapping, Control> buildPaneTransformer() {
		return new PaneTransformer();
	}

	protected class PaneTransformer
		extends AbstractTransformer<AttributeMapping, Control>
	{
		@Override
		public Control transform_(AttributeMapping attributeMapping) {
			return getMappingComposite(attributeMapping.getKey()).getControl();
		}
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

	private PropertyValueModel<AttributeMapping> buildMappingHolder(String key) {
		return new FilteringPropertyValueModel<AttributeMapping>(this.mappingHolder, this.buildKeyEquals(key));
	}

	private Predicate<AttributeMapping> buildKeyEquals(String mappingKey) {
		return new KeyEquals(mappingKey);
	}


	private ModifiablePropertyValueModel<AttributeMapping> buildMappingHolder() {
		return new PropertyAspectAdapter<A, AttributeMapping>(
			getSubjectHolder(),
			PersistentAttribute.MAPPING_PROPERTY)
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

	private class KeyEquals
		extends CriterionPredicate<AttributeMapping, String>
	{
		KeyEquals(String mappingKey) {
			super(mappingKey);
		}
		public boolean evaluate(AttributeMapping mapping) {
			return ((mapping == null) || (this.criterion == null)) || this.criterion.equals(mapping.getKey());
		}
	}
}
