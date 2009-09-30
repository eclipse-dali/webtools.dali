/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent attribute.
 *
 * @see PersistentAttribute
 *
 * @version 2.2
 * @since 1.0
 */
public abstract class PersistentAttributeDetailsPage<T extends PersistentAttribute> extends AbstractJpaDetailsPage<T>
{
	private Map<String, JpaComposite> mappingComposites;
	private PageBook mappingPageBook;

	/**
	 * Creates a new <code>PersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected PersistentAttributeDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(parent, widgetFactory);
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, JpaComposite>();
	}

	protected PageBook buildMappingPageBook(Composite parent) {
		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		this.mappingPageBook.showPage(this.addLabel(this.mappingPageBook, "")); //$NON-NLS-1$

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		this.mappingPageBook.setLayoutData(gridData);
		
		new ControlSwitcher(this.buildMappingHolder(), this.buildPaneTransformer(), this.mappingPageBook);

		return this.mappingPageBook;
	}
	
	private Transformer<AttributeMapping, Control> buildPaneTransformer() {
		return new Transformer<AttributeMapping, Control>() {
			public Control transform(AttributeMapping attributeMapping) {
				if (attributeMapping == null) {
					return null;
				}
				return getMappingComposite(attributeMapping.getKey()).getControl();
			}
		};
	}

	protected JpaComposite getMappingComposite(String key) {
		JpaComposite composite = this.mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}

		composite = buildMappingComposite(this.mappingPageBook, key);

		if (composite != null) {
			this.mappingComposites.put(key, composite);
		}

		return composite;
	}
	
	protected JpaComposite buildMappingComposite(PageBook pageBook, String key) {
		return getJpaPlatformUi().buildAttributeMappingComposite(
				getSubject().getResourceType(),
				key,
				pageBook,
				buildMappingHolder(key),
				getWidgetFactory());
	}
	
	private PropertyValueModel<AttributeMapping> buildMappingHolder(final String key) {
		return new FilteringPropertyValueModel<AttributeMapping>(
			buildMappingHolder(),
			buildMappingFilter(key)
		);
	}

	private Filter<AttributeMapping> buildMappingFilter(String mappingKey) {
		return new MappingFilter(mappingKey);
	}


	private WritablePropertyValueModel<AttributeMapping> buildMappingHolder() {
		return new PropertyAspectAdapter<T, AttributeMapping>(
			getSubjectHolder(),
			PersistentAttribute.DEFAULT_MAPPING_PROPERTY,
			PersistentAttribute.SPECIFIED_MAPPING_PROPERTY)
		{
			@Override
			protected AttributeMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}

	@Override
	protected void doDispose() {
		log(Tracing.UI_DETAILS_VIEW, "PersistentAttributeDetailsPage.doDispose()"); //$NON-NLS-1$

		this.mappingComposites.clear();
		super.doDispose();
	}

	@Override
	protected void log(String flag, String message) {
		super.log(flag, message);

		if (Tracing.UI_DETAILS_VIEW.equals(flag) &&
		    Tracing.booleanDebugOption(Tracing.UI_DETAILS_VIEW))
		{
			Tracing.log(message);
		}
	}

	private class MappingFilter implements Filter<AttributeMapping> {
		private String mappingKey;

		MappingFilter(String mappingKey) {
			super();
			this.mappingKey = mappingKey;
		}

		public boolean accept(AttributeMapping mapping) {
			return (mapping == null) || this.mappingKey.equals(mapping.getKey());
		}
	}
}