/*******************************************************************************
* Copyright (c) 2010, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 *  ClassesGeneratorOptionsWizardPage
 */
public class ClassesGeneratorOptionsWizardPage extends WizardPage
{
	private ProxyOptionsComposite proxyOptionsComposite;
	private Options1Composite options1Composite;
	private Options2Composite options2Composite;
	
	// ********** constructor **********

	protected ClassesGeneratorOptionsWizardPage() {
		super("Classes Generator Options"); //$NON-NLS-1$

		this.initialize();
	}

	protected void initialize() {
		this.setTitle(JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TITLE);
		this.setDescription(JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_DESC);
	}
		
	// ********** UI components **********

	public void createControl(Composite parent) {
		this.setPageComplete(true);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		this.proxyOptionsComposite = new ProxyOptionsComposite(composite);

		this.buildOptionsComposites(composite);
		
		return composite;
	}
	
	private void buildOptionsComposites(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, true));
		
		this.options1Composite = new Options1Composite(composite);

		this.options2Composite = new Options2Composite(composite);
	}

	// ********** intra-wizard methods **********

	protected String getProxy() {
		return this.proxyOptionsComposite.getProxy();
	}

	protected String getProxyFile() {
		return this.proxyOptionsComposite.getProxyFile();
	}
	
	protected boolean usesStrictValidation() {
		return this.options1Composite.usesStrictValidation();
	}
	
	protected boolean makesReadOnly() {
		return this.options1Composite.makesReadOnly();
	}

	protected boolean suppressesPackageInfoGen() {
		return this.options1Composite.suppressesPackageInfoGen();
	}
	
	protected boolean suppressesHeaderGen() {
		return this.options1Composite.suppressesHeaderGen();
	}
	
	protected boolean isVerbose() {
		return this.options1Composite.isVerbose();
	}
	
	protected boolean isQuiet() {
		return this.options1Composite.isQuiet();
	}

	protected boolean treatsAsXmlSchema() {
		return this.options2Composite.treatsAsXmlSchema();
	}
	
	protected boolean treatsAsRelaxNg() {
		return this.options2Composite.treatsAsRelaxNg();
	}
	
	protected boolean treatsAsRelaxNgCompact() {
		return this.options2Composite.treatsAsRelaxNgCompact();
	}
	
	protected boolean treatsAsDtd() {
		return this.options2Composite.treatsAsDtd();
	}
	
	protected boolean treatsAsWsdl() {
		return this.options2Composite.treatsAsWsdl();
	}
	
	protected boolean showsVersion() {
		return this.options2Composite.showsVersion();
	}
	
	protected boolean showsHelp() {
		return this.options2Composite.showsHelp();
	}

	// ********** UI controls **********
	
	protected Button buildCheckBox(Composite parent, String text, SelectionListener listener, int verticalIndent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		GridData gridData = new GridData();
		gridData.verticalIndent= verticalIndent;
		checkBox.setLayoutData(gridData);
		checkBox.setText(text);
		checkBox.addSelectionListener(listener);
		return checkBox;
	}

	protected Button buildRadioButton(Composite parent, String text, SelectionListener listener, int horizontalSpan) {
		Button radioButton = new Button(parent, SWT.RADIO);
		GridData gridData = new GridData();
		gridData.horizontalSpan = horizontalSpan;
		radioButton.setLayoutData(gridData);
		radioButton.setText(text);
		radioButton.addSelectionListener(listener);
		return radioButton;
	}

	protected Text buildText(Composite parent, int horizontalSpan) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = horizontalSpan;
		text.setLayoutData(gridData);
		return text;
	}

	protected void disableText(Text text) {
		text.setEnabled(false);
		text.setText(""); //$NON-NLS-1$
	}
	
	// ********** internal methods **********

	private String makeRelativeToProjectPath(String filePath) {
		Path path = new Path(filePath);
		IPath relativePath = path.makeRelativeTo(this.getProject().getLocation());
		return relativePath.toOSString();
	}

	private IProject getProject() {
		return ((ClassesGeneratorWizard)this.getWizard()).getJavaProject().getProject();
	}

	// ********** ProxyOptionsComposite **********

	class ProxyOptionsComposite {
		
		private final Button noProxyRadioButton;
		
		private final Button proxyRadioButton;
		private final Text proxyText;

		private final Button proxyFileRadioButton;
		private final Text proxyFileText;
		private Button browseButton;
		
		// ********** constructor **********

		private ProxyOptionsComposite(Composite parent) {
			super();
			Group proxyGroup = new Group(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			proxyGroup.setLayout(layout);
			proxyGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			proxyGroup.setText(JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_PROXY_GROUP);

			SelectionListener proxyButtonListener = this.buildProxyRadioButtonListener();

			this.noProxyRadioButton = buildRadioButton(proxyGroup, 
									JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_NO_PROXY, proxyButtonListener, 3);
			
			this.proxyRadioButton = buildRadioButton(proxyGroup, 
									JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_PROXY, proxyButtonListener, 1);
			this.proxyText = buildText(proxyGroup, 1);
			new Label(proxyGroup, SWT.WRAP); //empty label for spacing

			this.proxyFileRadioButton = buildRadioButton(proxyGroup, 
									JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_PROXY_FILE, proxyButtonListener, 1);
			this.proxyFileText = buildText(proxyGroup, 1);
			this.browseButton = this.buildBrowseButton(proxyGroup);
			
			this.noProxyRadioButton.setSelection(true);
			this.proxyButtonChanged();
		}

		private Button buildBrowseButton(Composite parent) {
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);

			// Browse buttons
			Button browseButton = new Button(buttonComposite, SWT.PUSH);
			browseButton.setText(JptJaxbUiMessages.CLASSES_GENERATOR_WIZARD_PAGE_BROWSE_BUTTON);
			gridData = new GridData();
			gridData.horizontalAlignment= GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			browseButton.setLayoutData(gridData);
			
			browseButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String filePath = promptProxyFile();
					if ( ! StringTools.isBlank(filePath)) {
						ProxyOptionsComposite.this.proxyFileText.setText(makeRelativeToProjectPath(filePath));
					}
				}
			});
			return browseButton;
		}

		// ********** listeners **********
		
		private SelectionListener buildProxyRadioButtonListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					proxyButtonChanged();
				}
			};
		}

		private void proxyButtonChanged() {
			boolean usesProxy = ! this.noProxyRadioButton.getSelection();
			if (usesProxy) {
				if (this.proxyRadioButton.getSelection()) {
					this.proxyText.setEnabled(true);
					disableText(this.proxyFileText);
					this.browseButton.setEnabled(false);
				}
				else if (this.proxyFileRadioButton.getSelection()) {
					this.proxyFileText.setEnabled(true);
					this.browseButton.setEnabled(true);
					disableText(this.proxyText);
				}
			}
			else {
				disableText(this.proxyText);
				disableText(this.proxyFileText);
				this.browseButton.setEnabled(false);
			}
		}

		// ********** internal methods **********
		/**
		 * The Add button was clicked, its action invokes this action which should
		 * prompt the user to select a file and return it.
		 */
		private String promptProxyFile() {
			IWizard wizard = ClassesGeneratorOptionsWizardPage.this.getWizard();
			String projectPath = ((ClassesGeneratorWizard)wizard).getJavaProject().getProject().getLocation().toString();

			FileDialog dialog = new FileDialog(getShell());
			dialog.setText(JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_CHOOSE_A_PROXY_FILE);
			dialog.setFilterPath(projectPath);

			return dialog.open();
		}

		// ********** intra-wizard methods **********

		protected String getProxy() {
			return this.proxyText.getText();
		}

		protected String getProxyFile() {
			return this.proxyFileText.getText();
		}
	}

	
	// ********** Options1Composite **********

	class Options1Composite {

		private boolean usesStrictValidation;
		private final Button usesStrictValidationCheckBox;

		private boolean makesReadOnly;
		private final Button makesReadOnlyCheckBox;
		
		private boolean suppressesPackageInfoGen;
		private final Button suppressesPackageInfoGenCheckBox;

		private boolean suppressesHeaderGen;
		private final Button suppressesHeaderGenCheckBox;

		private boolean isVerbose;
		private final Button isVerboseCheckBox;

		private boolean isQuiet;
		private final Button isQuietCheckBox;
		
		// ********** constructor **********

		Options1Composite(Composite parent) {
			super();
			this.usesStrictValidation = true;
			this.makesReadOnly = false;
			this.suppressesPackageInfoGen = false;
			this.suppressesHeaderGen = false;
			this.isVerbose = false;
			this.isQuiet = false;

			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());
			
			this.usesStrictValidationCheckBox = this.buildUsesStrictValidationCheckBox(composite, this.buildUsesStrictValidationSelectionListener());
			this.makesReadOnlyCheckBox = this.buildMakesReadOnlyCheckBox(composite, this.buildMakesReadOnlySelectionListener());
			this.suppressesPackageInfoGenCheckBox = this.buildSuppressesPackageInfoGenCheckBox(composite, this.buildSuppressesPackageInfoGenSelectionListener());
			this.suppressesHeaderGenCheckBox = this.buildSuppressesHeaderGenCheckBox(composite, this.buildSuppressesHeaderGenSelectionListener());
			this.isVerboseCheckBox = this.buildIsVerboseCheckBox(composite, this.buildIsVerboseSelectionListener());
			this.isQuietCheckBox = this.buildIsQuietCheckBox(composite, this.buildIsQuietSelectionListener());
		}

		// ********** UI components **********

		private Button buildUsesStrictValidationCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_USE_STRICT_VALIDATION, listener, 5);
			checkBox.setSelection(this.usesStrictValidation());
			return checkBox;
		}

		private Button buildMakesReadOnlyCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_MAKE_READ_ONLY, listener, 5);
			checkBox.setSelection(this.makesReadOnly());
			return checkBox;
		}

		private Button buildSuppressesPackageInfoGenCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_SUPPRESS_PACKAGE_INFO_GEN, listener, 5);
			checkBox.setSelection(this.suppressesPackageInfoGen());
			return checkBox;
		}
		
		private Button buildSuppressesHeaderGenCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_SUPPRESSES_HEADER_GEN, listener, 5);
			checkBox.setSelection(this.suppressesHeaderGen());
			return checkBox;
		}
	
		private Button buildIsVerboseCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_VERBOSE, listener, 5);
			checkBox.setSelection(this.isVerbose());
			return checkBox;
		}
		
		private Button buildIsQuietCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_QUIET, listener, 5);
			checkBox.setSelection(this.isQuiet());
			return checkBox;
		}

		// ********** listeners **********
		
		private SelectionListener buildUsesStrictValidationSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setUsesStrictValidation(
						Options1Composite.this.usesStrictValidationCheckBox.getSelection());
				}
			};
		}

		private SelectionListener buildMakesReadOnlySelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setMakesReadOnly(
						Options1Composite.this.makesReadOnlyCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildSuppressesPackageInfoGenSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setSuppressesPackageInfoGen(
						Options1Composite.this.suppressesPackageInfoGenCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildSuppressesHeaderGenSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setSuppressesHeaderGen(
						Options1Composite.this.suppressesHeaderGenCheckBox.getSelection());
				}
			};
		}

		private SelectionListener buildIsVerboseSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setIsVerbose(
						Options1Composite.this.isVerboseCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildIsQuietSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setIsQuiet(
						Options1Composite.this.isQuietCheckBox.getSelection());
				}
			};
		}

		// ********** getters/setters *********
		
		protected boolean usesStrictValidation() {
			return this.usesStrictValidation;
		}
		
		protected void setUsesStrictValidation(boolean usesStrictValidation){
			this.usesStrictValidation = usesStrictValidation;
		}
		
		protected boolean makesReadOnly() {
			return this.makesReadOnly;
		}
		
		protected void setMakesReadOnly(boolean makesReadOnly){
			this.makesReadOnly = makesReadOnly;
		}
		
		protected boolean suppressesPackageInfoGen() {
			return this.suppressesPackageInfoGen;
		}
		
		protected void setSuppressesPackageInfoGen(boolean suppressesPackageInfoGen){
			this.suppressesPackageInfoGen = suppressesPackageInfoGen;
		}
		
		protected boolean suppressesHeaderGen() {
			return this.suppressesHeaderGen;
		}
		
		protected void setSuppressesHeaderGen(boolean suppressesHeaderGen){
			this.suppressesHeaderGen = suppressesHeaderGen;
		}
		
		protected boolean isVerbose() {
			return this.isVerbose;
		}
		
		protected void setIsVerbose(boolean isVerbose){
			this.isVerbose = isVerbose;
		}
		
		protected boolean isQuiet() {
			return this.isQuiet;
		}
		
		protected void setIsQuiet(boolean isQuiet){
			this.isQuiet = isQuiet;
		}

	}

	// ********** Options2Composite **********

	class Options2Composite {

		private boolean treatsAsXmlSchema;
		private final Button treatsAsXmlSchemaCheckBox;

		private boolean treatsAsRelaxNg;
		private final Button treatsAsRelaxNgCheckBox;

		private boolean treatsAsRelaxNgCompact;
		private final Button treatsAsRelaxNgCompactCheckBox;

		private boolean treatsAsDtd;
		private final Button treatsAsDtdCheckBox;

		private boolean treatsAsWsdl;
		private final Button treatsAsWsdlCheckBox;

		private boolean showsVersion;
		private final Button showsVersionCheckBox;

		private boolean showsHelp;
		private final Button showsHelpCheckBox;
		
		// ********** constructor **********

		Options2Composite(Composite parent) {
			super();
			this.treatsAsXmlSchema = false;
			this.treatsAsRelaxNg = false;
			this.treatsAsRelaxNgCompact = false;
			this.treatsAsDtd = false;
			this.treatsAsWsdl = false;
			this.showsVersion = false;
			this.showsHelp = false;

			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());

			this.treatsAsXmlSchemaCheckBox = this.buildTreatsAsXmlSchemaCheckBox(composite, this.buildTreatsAsXmlSchemaSelectionListener());
			this.treatsAsRelaxNgCheckBox = this.buildTreatsAsRelaxNgCheckBox(composite, this.buildTreatsAsRelaxNgSelectionListener());
			this.treatsAsRelaxNgCompactCheckBox = this.buildTreatsAsRelaxNgCompactCheckBox(composite, this.buildTreatsAsRelaxNgCompactSelectionListener());
			this.treatsAsDtdCheckBox = this.buildTreatsAsDtdCheckBox(composite, this.buildTreatsAsDtdSelectionListener());
			this.treatsAsWsdlCheckBox = this.buildTreatsAsWsdlCheckBox(composite, this.buildTreatsAsWsdlSelectionListener());
			this.showsVersionCheckBox = this.buildVersionCheckBox(composite, this.buildVersionSelectionListener());
			this.showsHelpCheckBox = this.buildHelpCheckBox(composite, this.buildHelpSelectionListener());
		}

		// ********** UI components **********
		
		private Button buildTreatsAsXmlSchemaCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TREATS_AS_XML_SCHEMA, listener, 5);
			checkBox.setSelection(this.treatsAsXmlSchema());
			return checkBox;
		}

		private Button buildTreatsAsRelaxNgCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TREATS_AS_RELAX_NG, listener, 5);
			checkBox.setSelection(this.treatsAsRelaxNg());
			return checkBox;
		}
		private Button buildTreatsAsRelaxNgCompactCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TREATS_AS_RELAX_NG_COMPACT, listener, 5);
			checkBox.setSelection(this.treatsAsRelaxNgCompact());
			return checkBox;
		}

		private Button buildTreatsAsDtdCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TREATS_AS_DTD, listener, 5);
			checkBox.setSelection(this.treatsAsDtd());
			return checkBox;
		}

		private Button buildTreatsAsWsdlCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_TREATS_AS_WSDL, listener, 5);
			checkBox.setSelection(this.treatsAsWsdl());
			return checkBox;
		}
	
		private Button buildVersionCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_SHOWS_VERSION, listener, 5);
			checkBox.setSelection(this.showsVersion());
			return checkBox;
		}
	
		private Button buildHelpCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = buildCheckBox(parent, JptJaxbUiMessages.CLASSES_GENERATOR_OPTIONS_WIZARD_PAGE_SHOWS_HELP, listener, 5);
			checkBox.setSelection(this.showsHelp());
			return checkBox;
		}
		
		// ********** listeners **********

		private SelectionListener buildTreatsAsXmlSchemaSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setTreatsAsXmlSchema(
						Options2Composite.this.treatsAsXmlSchemaCheckBox.getSelection());
				}
			};
		}

		private SelectionListener buildTreatsAsRelaxNgSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setTreatsAsRelaxNg(
						Options2Composite.this.treatsAsRelaxNgCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildTreatsAsRelaxNgCompactSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setTreatsAsRelaxNgCompact(
						Options2Composite.this.treatsAsRelaxNgCompactCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildTreatsAsDtdSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setTreatsAsDtd(
						Options2Composite.this.treatsAsDtdCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildTreatsAsWsdlSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setTreatsAsWsdl(
						Options2Composite.this.treatsAsWsdlCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildVersionSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setShowsVersion(
						Options2Composite.this.showsVersionCheckBox.getSelection());
				}
			};
		}
		
		private SelectionListener buildHelpSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options2Composite.this.setShowsHelp(
						Options2Composite.this.showsHelpCheckBox.getSelection());
				}
			};
		}

		// ********** getter/setter *********

		protected boolean treatsAsXmlSchema() {
			return this.treatsAsXmlSchema;
		}
		
		protected void setTreatsAsXmlSchema(boolean treatsAsXmlSchema){
			this.treatsAsXmlSchema = treatsAsXmlSchema;
		}
		
		protected boolean treatsAsRelaxNg() {
			return this.treatsAsRelaxNg;
		}
		
		protected void setTreatsAsRelaxNg(boolean treatsAsRelaxNg){
			this.treatsAsRelaxNg = treatsAsRelaxNg;
		}
		
		protected boolean treatsAsRelaxNgCompact() {
			return this.treatsAsRelaxNgCompact;
		}
		
		protected void setTreatsAsRelaxNgCompact(boolean treatsAsRelaxNgCompact){
			this.treatsAsRelaxNgCompact = treatsAsRelaxNgCompact;
		}
		
		protected boolean treatsAsDtd() {
			return this.treatsAsDtd;
		}
		
		protected void setTreatsAsDtd(boolean treatsAsDtd){
			this.treatsAsDtd = treatsAsDtd;
		}
		
		protected boolean treatsAsWsdl() {
			return this.treatsAsWsdl;
		}
		
		protected void setTreatsAsWsdl(boolean treatsAsWsdl){
			this.treatsAsWsdl = treatsAsWsdl;
		}
		
		protected boolean showsVersion() {
			return this.showsVersion;
		}
		
		protected void setShowsVersion(boolean showsVersion){
			this.showsVersion = showsVersion;
		}
		
		protected boolean showsHelp() {
			return this.showsHelp;
		}
		
		protected void setShowsHelp(boolean showsHelp){
			this.showsHelp = showsHelp;
		}
	}
}
