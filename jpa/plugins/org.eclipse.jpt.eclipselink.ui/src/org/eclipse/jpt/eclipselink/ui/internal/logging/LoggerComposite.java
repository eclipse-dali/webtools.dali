/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.logging;

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
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logger;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
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
 *  LoggerComposite
 */
public class LoggerComposite extends Pane<Logging>
{
	/**
	 * Creates a new <code>LoggerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public LoggerComposite(
								Pane<? extends Logging> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultLoggerHolder() {
		return new PropertyAspectAdapter<Logging, String>(this.getSubjectHolder(), Logging.DEFAULT_LOGGER) {
			@Override
			protected String buildValue_() {
				return LoggerComposite.this.getDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultLoggerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultLoggerHolder()
		);
	}

	private String buildDisplayString(String loggerName) {

		switch (Logger.valueOf(loggerName)) {
			case default_logger: {
				return EclipseLinkUiMessages.LoggerComposite_default_logger;
			}
			case java_logger: {
				return EclipseLinkUiMessages.LoggerComposite_java_logger;
			}
			case server_logger: {
				return EclipseLinkUiMessages.LoggerComposite_server_logger;
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

	private StringConverter<String> buildLoggerConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
				try {
					Logger.valueOf(value);
					value = buildDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a Logger
				}
				return value;
			}
		};
	}

	private WritablePropertyValueModel<String> buildLoggerHolder() {
		return new PropertyAspectAdapter<Logging, String>(this.getSubjectHolder(), Logging.LOGGER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getLogger();
				if (name == null) {
					name = LoggerComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setLogger(value);
			}
		};
	}

	private ListValueModel<String> buildLoggerListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(this.buildDefaultLoggerListHolder());
		holders.add(this.buildLoggersListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private Iterator<String> buildLoggers() {
		return new TransformationIterator<Logger, String>(CollectionTools.iterator(Logger.values())) {
			@Override
			protected String transform(Logger next) {
				return next.name();
			}
		};
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

	private String getDefaultValue(Logging subject) {
		String defaultValue = subject.getDefaultLogger();

		if (defaultValue != null) {
			return NLS.bind(
				EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlLoggingTab_defaultEmpty;
		}
	}
	
    @Override
    protected void initializeLayout(Composite container) {

    	CCombo combo = this.addLoggerCCombo(container);

		this.addLabeledComposite(
			container,
			this.addLeftControl(container),
			combo.getParent(),
			this.addBrowseButton(container),
			null
		);
    }

    protected CCombo addLoggerCCombo(Composite container) {

		return this.addEditableCCombo(
			container,
			this.buildLoggerListHolder(),
			this.buildLoggerHolder(),
			this.buildLoggerConverter()
		);
    }

	protected Control addLeftControl(Composite container) {
		return this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggerLabel);
	}
	
	protected Button addBrowseButton(Composite parent) {
		return this.addPushButton(
			parent,
			EclipseLinkUiMessages.PersistenceXmlLoggingTabb_browse,
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
			this.getSubject().setLogger(className);
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
		return this.getSubject().getLogger();
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