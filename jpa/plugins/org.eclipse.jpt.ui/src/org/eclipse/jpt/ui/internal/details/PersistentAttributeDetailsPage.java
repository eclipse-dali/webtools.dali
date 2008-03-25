/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent attribute.
 *
 * @see PersistentAttribute
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class PersistentAttributeDetailsPage<T extends PersistentAttribute> extends AbstractJpaDetailsPage<T>
{
	private JpaComposite<AttributeMapping> currentMappingComposite;
	private String currentMappingKey;
	private Map<String, JpaComposite<AttributeMapping>> mappingComposites;
	private PageBook mappingPageBook;

	/**
	 * Creates a new <code>PersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected PersistentAttributeDetailsPage(Composite parent,
                                            WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentAttribute.DEFAULT_MAPPING_PROPERTY);
		propertyNames.add(PersistentAttribute.SPECIFIED_MAPPING_PROPERTY);
	}

	protected AttributeMappingUiProvider<? extends AttributeMapping> attributeMappingUiProvider(String key) {
		for (Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> i = attributeMappingUiProviders(); i.hasNext(); ) {
			AttributeMappingUiProvider<? extends AttributeMapping> provider = i.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping UI provider key: ");
	}

	protected abstract Iterator<AttributeMappingUiProvider<? extends AttributeMapping>>
		attributeMappingUiProviders();

	protected abstract AttributeMappingUiProvider<? extends AttributeMapping>[]
		attributeMappingUiProvidersFor(PersistentAttribute persistentAttribute);

	private PropertyAspectAdapter<PersistentAttribute, AttributeMapping> buildGenericMappingHolder() {
		return new PropertyAspectAdapter<PersistentAttribute, AttributeMapping>(
			getSubjectHolder(),
			PersistentAttribute.DEFAULT_MAPPING_PROPERTY,
			PersistentAttribute.SPECIFIED_MAPPING_PROPERTY)
		{
			@Override
			protected AttributeMapping buildValue_() {
				return subject.getMapping();
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected JpaComposite<AttributeMapping> buildMappingComposite(PageBook pageBook,
	                                                               String mappingKey) {

		AttributeMappingUiProvider<AttributeMapping> uiProvider =
			(AttributeMappingUiProvider<AttributeMapping>) mappingUIProvider(mappingKey);

		return uiProvider.buildAttributeMappingComposite(
			jpaUiFactory(),
			buildMappingHolder(mappingKey),
			pageBook,
			getWidgetFactory()
		);
	}

	private Filter<AttributeMapping> buildMappingFilter(String mappingKey) {
		return new MappingFilter(mappingKey);
	}

	private PropertyValueModel<AttributeMapping> buildMappingHolder(final String key) {
		return new FilteringPropertyValueModel<AttributeMapping>(
			buildGenericMappingHolder(),
			buildMappingFilter(key)
		);
	}

	protected Label buildMappingLabel(Composite parent) {
		return buildLabel(parent, JptUiMessages.PersistentAttributePage_mapAs);
	}

	protected PageBook buildMappingPageBook(Composite parent) {
		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		this.mappingPageBook.showPage(this.buildLabel(this.mappingPageBook, ""));
		return this.mappingPageBook;
	}

	protected abstract AttributeMappingUiProvider<? extends AttributeMapping>
		defaultAttributeMappingUiProvider(String key);

	protected abstract Iterator<AttributeMappingUiProvider<? extends AttributeMapping>>
		defaultAttributeMappingUiProviders();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doDispose() {
		log(Tracing.UI_DETAILS_VIEW, "PersistentAttributeDetailsPage.doDispose()");

		if (this.currentMappingComposite != null) {
			this.currentMappingComposite.dispose();
			this.currentMappingComposite = null;
		}

		this.mappingComposites.clear();
		super.doDispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateMappingPage();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, JpaComposite<AttributeMapping>>();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void log(String flag, String message) {
		super.log(flag, message);

		if (Tracing.UI_DETAILS_VIEW.equals(flag) &&
		    Tracing.booleanDebugOption(Tracing.UI_DETAILS_VIEW))
		{
			Tracing.log(message);
		}
	}

	private JpaComposite<AttributeMapping> mappingCompositeFor(String key) {
		JpaComposite<AttributeMapping> composite = this.mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}

		composite = buildMappingComposite(this.mappingPageBook, key);

		if (composite != null) {
			this.mappingComposites.put(key, composite);
		}

		return composite;
	}

	private AttributeMappingUiProvider<? extends AttributeMapping> mappingUIProvider(String key) {

		if (this.subject().getMapping() == null ||
		    this.subject().getMapping().isDefault()) {

			return defaultAttributeMappingUiProvider(key);
		}

		return attributeMappingUiProvider(key);
	}

	private void populateMappingPage(String mappingKey) {

		// Nothing to update
		if (this.currentMappingKey == mappingKey) {
			return;
		}
		// Dispose the existing mapping pane
		else if (this.currentMappingComposite != null) {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentAttributeDetailsPage.populateMappingPage() disposing of current page: " + this.currentMappingKey
			);

			try {
				this.currentMappingComposite.dispose();
				this.currentMappingComposite = null;
			}
			catch (Exception e) {
				JptUiPlugin.log(e);
			}
		}

		this.currentMappingKey = mappingKey;

		// Change the current mapping pane with the new one
		if (this.currentMappingKey != null) {
			this.currentMappingComposite = mappingCompositeFor(mappingKey);

			try {
				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentAttributeDetailsPage.populateMappingPage() populating new page: " + this.currentMappingKey
				);

				this.currentMappingComposite.populate();
				this.mappingPageBook.showPage(this.currentMappingComposite.getControl());
				this.repaintDetailsView(this.mappingPageBook);
			}
			catch (Exception e) {
				JptUiPlugin.log(e);

				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentAttributeDetailsPage.populateMappingPage() error encountered"
				);

				// An error was encountered either during the population, dispose it
				try {
					this.currentMappingComposite.dispose();
				}
				catch (Exception exception) {
					JptUiPlugin.log(e);
				}

				this.mappingComposites.remove(this.currentMappingComposite);
				this.currentMappingComposite = null;

				// Show an error message
				// TODO: Replace the blank label with the error page
				this.mappingPageBook.showPage(this.buildLabel(this.mappingPageBook, ""));
			}
		}
		else {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentAttributeDetailsPage.populateMappingPage() no page to show"
			);

			this.mappingPageBook.showPage(this.buildLabel(this.mappingPageBook, ""));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentAttribute.DEFAULT_MAPPING_PROPERTY ||
		    propertyName == PersistentAttribute.SPECIFIED_MAPPING_PROPERTY) {

			updateMappingPage();
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected boolean repopulateWithNullSubject() {
		return false;
	}

	private void updateMappingPage() {
		AttributeMapping mapping = (this.subject() != null) ? this.subject().getMapping() : null;
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}

	private class MappingFilter implements Filter<AttributeMapping> {
		private String mappingKey;

		MappingFilter(String mappingKey) {
			super();
			this.mappingKey = mappingKey;
		}

		public boolean accept(AttributeMapping mapping) {
			return (mapping == null) || mappingKey.equals(mapping.getKey());
		}
	}
}