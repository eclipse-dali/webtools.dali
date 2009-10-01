/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent type.
 *
 * @see PersistentType
 *
 * @version 2.2
 * @since 1.0
 */
@SuppressWarnings("nls")
public class PersistentTypeDetailsPage extends AbstractJpaDetailsPage<PersistentType>
{
	private Map<String, JpaComposite> mappingComposites;
	private PageBook mappingPageBook;

	/**
	 * Creates a new <code>PersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentTypeDetailsPage(Composite parent,
                                    WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, JpaComposite>();
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		// Map As composite
		new PersistentTypeMapAsComposite(
			this,
			addSubPane(container, 0, 0, 5, 0)
		);

		// Type properties page
		buildMappingPageBook(container);
	}

	protected PageBook buildMappingPageBook(Composite parent) {

		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		this.mappingPageBook.showPage(this.addLabel(this.mappingPageBook, ""));

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		this.mappingPageBook.setLayoutData(gridData);
		
		new ControlSwitcher(this.buildMappingHolder(), this.buildPaneTransformer(), this.mappingPageBook);

		return this.mappingPageBook;
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
			buildMappingHolder(),
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
			public boolean accept(TypeMapping value) {
				return (value == null) || key.equals(value.getKey());
			}
		};
	}

	
	private JpaComposite getMappingComposite(String key) {
		JpaComposite mappingComposite = this.mappingComposites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.mappingPageBook, key);

		if (mappingComposite != null) {
			this.mappingComposites.put(key, mappingComposite);
		}

		return mappingComposite;
	}
	
	protected JpaComposite buildMappingComposite(PageBook pageBook, String key) {
		return getJpaPlatformUi().
			buildTypeMappingComposite(
				getSubject().getResourceType(), 
				key, 
				pageBook, 
				buildMappingHolder(key), 
				getWidgetFactory());
	}

	@Override
	public void dispose() {
		log(Tracing.UI_DETAILS_VIEW, "PersistentTypeDetailsPage.dispose()");

		for (JpaComposite mappingComposite : this.mappingComposites.values()) {
			mappingComposite.dispose();
		}
		this.mappingComposites.clear();
		super.dispose();
	}

}