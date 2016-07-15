/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkProfiler;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import com.ibm.icu.text.Collator;

/**
 *  ProfilerComposite
 */
public class EclipseLinkProfilerClassChooser
	extends ClassChooserComboPane<EclipseLinkCustomization>
{

	/**
	 * Creates a new <code>ProfilerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkProfilerClassChooser(
								Pane<? extends EclipseLinkCustomization> parentPane,
	                           Composite parent,
	                           Hyperlink hyperlink) {

		super(parentPane, parent, hyperlink);
	}

	@Override
	protected String getClassName() {
		return EclipseLinkProfiler.getProfilerClassName(this.getSubject().getProfiler());
	}
   
	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

    @Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, String>(this.getSubjectHolder(), EclipseLinkCustomization.PROFILER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getProfiler();
				if (name == null) {
					name = EclipseLinkProfilerClassChooser.this.getDefaultValue(this.subject);
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

	private PropertyValueModel<String> buildDefaultProfilerModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, String>(this.getSubjectHolder(), EclipseLinkCustomization.DEFAULT_PROFILER) {
			@Override
			protected String buildValue_() {
				return EclipseLinkProfilerClassChooser.this.getDefaultValue(this.subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultProfilerListModel() {
		return new PropertyListValueModelAdapter<>(
			this.buildDefaultProfilerModel()
		);
	}

	String buildDisplayString(String profilerName) {

		switch (EclipseLinkProfiler.valueOf(profilerName)) {
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
		return new ClassTransformer();
	}

	class ClassTransformer
		extends TransformerAdapter<String, String>
	{
		@Override
		public String transform(String value) {
			try {
				return buildDisplayString(value);
			} catch (RuntimeException ex) {
				// the value is not a Logger
				return value;
			}
		}
	}

	@Override
	protected ListValueModel<String> buildClassListModel() {
		ArrayList<ListValueModel<String>> models = new ArrayList<>(2);
		models.add(this.buildDefaultProfilerListModel());
		models.add(this.buildProfilersListModel());
		return CompositeListValueModel.forModels(models);
	}

	private Iterator<String> buildProfilers() {
		return IteratorTools.transform(IteratorTools.iterator(EclipseLinkProfiler.values()), PersistenceXmlEnumValue.ENUM_NAME_TRANSFORMER);
	}

	private CollectionValueModel<String> buildProfilersCollectionModel() {
		return new SimpleCollectionValueModel<>(
			CollectionTools.hashBag(this.buildProfilers())
		);
	}

	private ListValueModel<String> buildProfilersListModel() {
		return new SortedListValueModelAdapter<>(
			this.buildProfilersCollectionModel(),
			this.buildProfilerComparator()
		);
	}

	String getDefaultValue(EclipseLinkCustomization subject) {
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
		return EclipseLinkCustomization.ECLIPSELINK_SESSION_PROFILER_CLASS_NAME;
	}
}
