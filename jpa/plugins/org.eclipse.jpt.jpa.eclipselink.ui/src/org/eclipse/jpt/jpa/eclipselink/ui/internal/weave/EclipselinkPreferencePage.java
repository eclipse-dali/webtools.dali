/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.weave;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.eclipselink.core.internal.builder.EclipselinkStaticWeavingBuilder;
import org.eclipse.jpt.jpa.eclipselink.core.internal.weave.StaticWeavePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class EclipselinkPreferencePage extends PropertyPage {

	private StaticWeavingComposite staticWeavingComposite;
	
	// ********** constructors **********
	
	public EclipselinkPreferencePage() {
		super();
		initialize();
	}

	protected void initialize() {
		
	}
	
	// ********** overrides **********

	@Override
	public boolean performOk() {
		super.performOk();

		if(this.staticWeaveClasses()) {
			if( ! this.projectHasStaticWeavingBuilder()) {
				EclipselinkStaticWeavingBuilder.addBuilder(this.getProject());
			}
			this.updateProjectStaticWeavingPreferences();
		}
		else {
			EclipselinkStaticWeavingBuilder.removeBuilder(this.getProject());
			this.removeProjectStaticWeavingPreferences();
		}
		return true;
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		this.noDefaultAndApplyButton();
		this.staticWeavingComposite = new StaticWeavingComposite(composite);
		
		return composite;
	}
	
	// ********** preferences **********
	
	private void updateProjectStaticWeavingPreferences() {
		this.setPreference(StaticWeavePreferences.SOURCE, this.getSourceFolder());
		this.setPreference(StaticWeavePreferences.TARGET, this.getTargetFolder());
		this.setPreference(StaticWeavePreferences.LOG_LEVEL, this.getLogLevel());
		this.setPreference(StaticWeavePreferences.PERSISTENCE_INFO, this.getPersistenceInfo());
	}

	private void removeProjectStaticWeavingPreferences() {
		this.setPreference(StaticWeavePreferences.SOURCE, null);
		this.setPreference(StaticWeavePreferences.TARGET, null);
		this.setPreference(StaticWeavePreferences.LOG_LEVEL, null);
		this.setPreference(StaticWeavePreferences.PERSISTENCE_INFO, null);
	}
	
	private void setPreference(String id, String value) {
		StaticWeavePreferences.setPreference(this.getProject(), id, value);
	}

	// ********** internal methods **********
	
	private boolean projectHasStaticWeavingBuilder() {
		return EclipselinkStaticWeavingBuilder.projectHasStaticWeavingBuilder(this.getProject());
	}

	private IProject getProject() {
        IAdaptable adaptable= this.getElement();
		return adaptable == null ? null : (IProject)adaptable.getAdapter(IProject.class);
	}

	private String makeRelativeToProjectPath(String filePath) {
		Path path = new Path(filePath);
		IPath relativePath = path.makeRelativeTo(this.getProject().getLocation());
		return relativePath.toOSString();
	}

	// ********** getters *********
	
	private boolean staticWeaveClasses() {
		return this.staticWeavingComposite.staticWeaveCheckBox();
	}
	private String getSourceFolder() {
		return this.staticWeavingComposite.getSourceFolder();
	}
	private String getTargetFolder() {
		return this.staticWeavingComposite.getTargetFolder();
	}
	private String getPersistenceInfo() {
		return this.staticWeavingComposite.getPersistenceInfo();
	}
	private String getLogLevel() {
		return this.staticWeavingComposite.getLogLevel();
	}
	
	// ********** StaticWeavingComposite **********

	class StaticWeavingComposite {
		private final Button staticWeaveClassesCheckBox;
		private final Label sourceLabel;
		private final Text sourceFolderText;
		private Button browseSourceButton;

		// ********** constructor **********

		private StaticWeavingComposite(Composite parent) {
			super();
			Group weavingGroup = new Group(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			weavingGroup.setLayout(layout);
			weavingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			weavingGroup.setText("Static weaving");	//TODO

			SelectionListener staticWeaveCheckBoxListener = this.buildStaticWeaveCheckBoxListener();

			this.staticWeaveClassesCheckBox = this.buildStaticWeaveCheckBox(weavingGroup, 
						"Weave classes on build", staticWeaveCheckBoxListener, 3);
			this.sourceLabel = this.buildLabel(weavingGroup, 1, "Source:"); //TODO
			this.sourceFolderText = this.buildText(weavingGroup, 1);
			this.browseSourceButton = this.buildBrowseButton(weavingGroup,
									this.buildBrowseSourceButtonSelectionListener(
										"Select Source", 
										"Source folder selection"));	//TODO

			this.initialize();
		}

		private void initialize() {
			this.staticWeaveClassesCheckBox.setSelection(projectHasStaticWeavingBuilder());
			if(this.staticWeaveCheckBox()) {
				this.initializeFromPreferences();
			}
			this.staticWeaveCheckBoxChanged();
		}

		private void initializeFromPreferences() {
			//TODO - populate from preferences
			if(StringTools.stringIsEmpty(this.sourceFolderText.getText())) {
				this.sourceFolderText.setText(getSourcePreference());
			}
			
		}
		
		private String getSourcePreference() {
			return EclipselinkStaticWeavingBuilder.getSourcePreference(getProject());
		}
		
		// ********** listeners **********
		
		private SelectionListener buildStaticWeaveCheckBoxListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					staticWeaveCheckBoxChanged();
				}
			};
		}

		private void staticWeaveCheckBoxChanged() {
			this.setSourceEnabled(this.staticWeaveCheckBox());
			if(this.staticWeaveCheckBox()) {
				this.initializeFromPreferences();
			}
		}

		// ********** UI controls **********

		private void setSourceEnabled(boolean enabled) {
			this.sourceLabel.setEnabled(enabled);
			this.sourceFolderText.setEnabled(enabled);
			this.browseSourceButton.setEnabled(enabled);
		}
		
		private Button buildBrowseButton(Composite parent, SelectionListener selectionListener) {
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);

			// Browse buttons
			Button browseButton = new Button(buttonComposite, SWT.PUSH);
			browseButton.setText("Browse");		//TODO
			gridData = new GridData();
			gridData.horizontalAlignment= GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			browseButton.setLayoutData(gridData);
			
			browseButton.addSelectionListener(selectionListener);
			return browseButton;
		}

		private SelectionListener buildBrowseSourceButtonSelectionListener(final String title, final String description) {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String directory = promptFolder(title, description);
					if ( ! StringTools.stringIsEmpty(directory)) {
						StaticWeavingComposite.this.sourceFolderText.setText(makeRelativeToProjectPath(directory));
					}
				}
			};
		}

		private Button buildStaticWeaveCheckBox(Composite parent, String text, SelectionListener listener, int horizontalSpan) {
			Button radioButton = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.horizontalSpan = horizontalSpan;
			radioButton.setLayoutData(gridData);
			radioButton.setText(text);
			radioButton.addSelectionListener(listener);
			return radioButton;
		}

		private Text buildText(Composite parent, int horizontalSpan) {
			Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gridData.horizontalSpan = horizontalSpan;
			text.setLayoutData(gridData);
			return text;
		}
		
		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gridData = new GridData();
			gridData.horizontalSpan = span;
			label.setLayoutData(gridData);
			return label;
		}

		// ********** getters *********
		
		private String getSourceFolder() {
			String sourceFolder = this.sourceFolderText.getText();
			
			return sourceFolder;
		}
		private String getTargetFolder() {
			return null;
//					return this.targetFolderText.getText();
		}
		private String getPersistenceInfo() {
			return null;
//					return this.persistenceInfoText.getText();
		}
		private String getLogLevel() {
			return "FINEST";
//					return this.logLevel.getText();
		}

		private boolean staticWeaveCheckBox() {
			return this.staticWeaveClassesCheckBox.getSelection();
		}
		
		// ********** internal methods **********
		
		/**
		 * The browse button was clicked, its action invokes this action which should
		 * prompt the user to select a folder and set it.
		 */
		private String promptFolder(String title, String description) {

			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setText(title);
			dialog.setMessage(description);
			dialog.setFilterPath(this.filterPath());
			String directory = dialog.open();
			return directory;
		}
		
		protected String filterPath() {
			return getProject().getLocation().toPortableString();
		}
	}
	
}