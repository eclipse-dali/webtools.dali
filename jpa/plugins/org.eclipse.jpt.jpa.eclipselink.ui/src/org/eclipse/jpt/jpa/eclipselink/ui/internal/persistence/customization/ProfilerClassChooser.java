/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Profiler;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import com.ibm.icu.text.Collator;

/**
 *  ProfilerComposite
 */
public class ProfilerClassChooser extends ClassChooserComboPane<Customization>
{

	/**
	 * Creates a new <code>ProfilerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ProfilerClassChooser(
								Pane<? extends Customization> parentPane,
	                           Composite parent,
	                           Hyperlink hyperlink) {

		super(parentPane, parent, hyperlink);
	}

	@Override
	protected String getClassName() {
		return Profiler.getProfilerClassName(this.getSubject().getProfiler());
	}
   
	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

    @Override
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<Customization, String>(this.getSubjectHolder(), Customization.PROFILER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getProfiler();
				if (name == null) {
					name = ProfilerClassChooser.this.getDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setProfiler(value);
			}
		};
    }

	private PropertyValueModel<String> buildDefaultProfilerHolder() {
		return new PropertyAspectAdapter<Customization, String>(this.getSubjectHolder(), Customization.DEFAULT_PROFILER) {
			@Override
			protected String buildValue_() {
				return ProfilerClassChooser.this.getDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultProfilerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultProfilerHolder()
		);
	}

	private String buildDisplayString(String profilerName) {

		switch (Profiler.valueOf(profilerName)) {
			case no_profiler: {
				return JptJpaEclipseLinkUiMessages.PROFILER_COMPOSITE_NO_PROFILER;
			}
			case performance_profiler: {
				return JptJpaEclipseLinkUiMessages.PROFILER_COMPOSITE_PERFORMANCE_PROFILER;
			}
			case query_monitor: {
				return JptJpaEclipseLinkUiMessages.PROFILER_COMPOSITE_QUERY_MONITOR;
			}
			default: {
				return null;
			}
		}
	}

	private Comparator<String> buildProfilerComparator() {
		return new Comparator<String>() {
			public int compare(String profiler1, String profiler2) {
				profiler1 = buildDisplayString(profiler1);
				profiler2 = buildDisplayString(profiler2);
				return Collator.getInstance().compare(profiler1, profiler2);
			}
		};
	}

	@Override
	protected Transformer<String, String> buildClassConverter() {
		return new TransformerAdapter<String, String>() {
			@Override
			public String transform(String value) {
				try {
					Profiler.valueOf(value);
					value = buildDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a Profiler
				}
				return value;
			}
		};
	}

	@Override
	protected ListValueModel<String> buildClassListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(this.buildDefaultProfilerListHolder());
		holders.add(this.buildProfilersListHolder());
		return CompositeListValueModel.forModels(holders);
	}

	private Iterator<String> buildProfilers() {
		return IteratorTools.transform(IteratorTools.iterator(Profiler.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildProfilersCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(this.buildProfilers())
		);
	}

	private ListValueModel<String> buildProfilersListHolder() {
		return new SortedListValueModelAdapter<String>(
			this.buildProfilersCollectionHolder(),
			this.buildProfilerComparator()
		);
	}

	private String getDefaultValue(Customization subject) {
		String defaultValue = subject.getDefaultProfiler();

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
		this.getSubject().setProfiler(className);
	}

	@Override
	protected String getSuperInterfaceName() {
		return Customization.ECLIPSELINK_SESSION_PROFILER_CLASS_NAME;
	}
}