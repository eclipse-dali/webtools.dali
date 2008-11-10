/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.customization;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Profiler;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  ProfilerComposite
 */
public class ProfilerComposite extends Pane<Customization>
{
	/**
	 * Creates a new <code>ProfilerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ProfilerComposite(
								Pane<? extends Customization> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultProfilerHolder() {
		return new PropertyAspectAdapter<Customization, String>(this.getSubjectHolder(), Customization.DEFAULT_PROFILER) {
			@Override
			protected String buildValue_() {
				return ProfilerComposite.this.getDefaultValue(subject);
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
				return EclipseLinkUiMessages.ProfilerComposite_no_profiler;
			}
			case dms_performance_profiler: {
				return EclipseLinkUiMessages.ProfilerComposite_dms_performance_profiler;
			}
			case performance_profiler: {
				return EclipseLinkUiMessages.ProfilerComposite_performance_profiler;
			}
			case query_monitor: {
				return EclipseLinkUiMessages.ProfilerComposite_query_monitor;
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

	private StringConverter<String> buildProfilerConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
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

	private WritablePropertyValueModel<String> buildProfilerHolder() {
		return new PropertyAspectAdapter<Customization, String>(this.getSubjectHolder(), Customization.PROFILER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getProfiler();
				if (name == null) {
					name = ProfilerComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setProfiler(value);
			}
		};
	}

	private ListValueModel<String> buildProfilerListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(this.buildDefaultProfilerListHolder());
		holders.add(this.buildProfilersListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private Iterator<String> buildProfilers() {
		return new TransformationIterator<Profiler, String>(CollectionTools.iterator(Profiler.values())) {
			@Override
			protected String transform(Profiler next) {
				return next.name();
			}
		};
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
				EclipseLinkUiMessages.PersistenceXmlCustomizationTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_defaultEmpty;
		}
	}
	
    @Override
    protected void initializeLayout(Composite container) {

    	CCombo combo = this.addProfilerCCombo(container);

		this.addLabeledComposite(
			container,
			this.addLeftControl(container),
			combo.getParent(),
			this.addBrowseButton(container),
			null
		);
    }

    protected CCombo addProfilerCCombo(Composite container) {

		return this.addEditableCCombo(
			container,
			this.buildProfilerListHolder(),
			this.buildProfilerHolder(),
			this.buildProfilerConverter()
		);
    }

	protected Control addLeftControl(Composite container) {
		return this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlCustomizationTab_profilerLabel);
	}
	
	protected Button addBrowseButton(Composite parent) {
		return this.addPushButton(
			parent,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_browse,
			this.buildBrowseAction()
		);
	}
	
	private Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptType();
			}
		};
	}
	
	protected void promptType() {
		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName('.');
			this.getSubject().setProfiler(className);
		}
	}

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * cancelled the dialog
	 */
	protected IType chooseType() {

		IPackageFragmentRoot root = this.getPackageFragmentRoot();

		if (root == null) {
			return null;
		}

		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				getShell(),
				service,
				scope,
				IJavaElementSearchConstants.CONSIDER_CLASSES,
				false,
				this.getClassName() != null ? ClassTools.shortNameForClassNamed(this.getClassName()) : ""
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptUiMessages.ClassChooserPane_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	protected String getClassName() {
		return this.getSubject().getProfiler();
	}

	protected IPackageFragmentRoot getPackageFragmentRoot() {
		IProject project = this.getSubject().getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);

		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptEclipseLinkUiPlugin.log(e);
		}
		return null;
	}
}