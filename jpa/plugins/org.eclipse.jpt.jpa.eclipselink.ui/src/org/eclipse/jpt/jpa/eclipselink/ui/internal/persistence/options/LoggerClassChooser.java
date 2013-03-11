/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogger;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import com.ibm.icu.text.Collator;

/**
 *  LoggerComposite
 */
public class LoggerClassChooser extends ClassChooserComboPane<EclipseLinkLogging>
{
	/**
	 * Creates a new <code>LoggerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public LoggerClassChooser(Pane<? extends EclipseLinkLogging> parentPane,
	                           Composite parent,
	                           Hyperlink hyperlink) {

		super(parentPane, parent, hyperlink);
	}
	
	@Override
	protected String getClassName() {
		if (this.getSubject().getLogger() == null) {
			return EclipseLinkLogger.default_logger.getClassName();
		}
		return EclipseLinkLogger.getLoggerClassName(this.getSubject().getLogger());
	}
    
	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}
    
    @Override
    protected String getSuperInterfaceName() {
    	return EclipseLinkLogging.ECLIPSELINK_LOGGER_CLASS_NAME;
    }
    
	@Override
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkLogging, String>(this.getSubjectHolder(), EclipseLinkLogging.LOGGER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getLogger();
				if (name == null) {
					name = LoggerClassChooser.this.getDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setLogger(value);
			}
		};
	}

	private PropertyValueModel<String> buildDefaultLoggerHolder() {
		return new PropertyAspectAdapter<EclipseLinkLogging, String>(this.getSubjectHolder(), EclipseLinkLogging.DEFAULT_LOGGER) {
			@Override
			protected String buildValue_() {
				return LoggerClassChooser.this.getDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultLoggerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultLoggerHolder()
		);
	}

	private String buildDisplayString(String loggerName) {

		switch (EclipseLinkLogger.valueOf(loggerName)) {
			case default_logger: {
				return JptJpaEclipseLinkUiMessages.LOGGER_COMPOSITE_DEFAULT_LOGGER;
			}
			case java_logger: {
				return JptJpaEclipseLinkUiMessages.LOGGER_COMPOSITE_JAVA_LOGGER;
			}
			case server_logger: {
				return JptJpaEclipseLinkUiMessages.LOGGER_COMPOSITE_SERVER_LOGGER;
			}
			default: {
				return null;
			}
		}
	}

	private Comparator<String> buildLoggerComparator() {
		return new Comparator<String>() {
			public int compare(String logger1, String logger2) {
				logger1 = buildDisplayString(logger1);
				logger2 = buildDisplayString(logger2);
				return Collator.getInstance().compare(logger1, logger2);
			}
		};
	}

	@Override
	protected Transformer<String, String> buildClassConverter() {
		return new TransformerAdapter<String, String>() {
			@Override
			public String transform(String value) {
				try {
					EclipseLinkLogger.valueOf(value);
					value = buildDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a Logger
				}
				return value;
			}
		};
	}

	@Override
	protected ListValueModel<String> buildClassListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(this.buildDefaultLoggerListHolder());
		holders.add(this.buildLoggersListHolder());
		return CompositeListValueModel.forModels(holders);
	}

	private Iterator<String> buildLoggers() {
		return IteratorTools.transform(IteratorTools.iterator(EclipseLinkLogger.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildLoggersCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(this.buildLoggers())
		);
	}

	private ListValueModel<String> buildLoggersListHolder() {
		return new SortedListValueModelAdapter<String>(
			this.buildLoggersCollectionHolder(),
			this.buildLoggerComparator()
		);
	}

	private String getDefaultValue(EclipseLinkLogging subject) {
		String defaultValue = subject.getDefaultLogger();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}
	
	@Override
	protected void setClassName(String className) {
		this.getSubject().setLogger(className);
	}
}