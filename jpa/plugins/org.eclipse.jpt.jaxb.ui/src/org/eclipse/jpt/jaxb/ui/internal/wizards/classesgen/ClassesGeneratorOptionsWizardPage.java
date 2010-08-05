/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
		this.setTitle(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_title);
		this.setDescription(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_desc);
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
	
	protected boolean getTarget() {
		return this.options1Composite.targetIs20();
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

	
	// ********** ProxyOptionsComposite **********

	class ProxyOptionsComposite {

		private final Text proxyText;
		private final Text proxyFileText;
		
		// ********** constructor **********

		private ProxyOptionsComposite(Composite parent) {
			super();
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			// Proxy
			Label proxyLabel = new Label(composite, SWT.NONE);
			proxyLabel.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_proxy);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			proxyLabel.setLayoutData(gridData);
			this.proxyText = this.buildProxyText(composite);
			
			// ProxyFile
			Label proxyFileLabel = new Label(composite, SWT.NONE);
			proxyFileLabel.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_proxyFile);
			gridData = new GridData();
			gridData.verticalIndent = 5;
			proxyFileLabel.setLayoutData(gridData);
			this.proxyFileText = this.buildProxyFileText(composite);
		}
		
		// ********** UI components **********

		private Text buildProxyText(Composite parent) {
			Text text = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			gridData.verticalIndent = 5;
			text.setLayoutData(gridData);
			return text;
		}

		private Text buildProxyFileText(Composite parent) {
			Text text = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			gridData.verticalIndent = 5;
			text.setLayoutData(gridData);
			return text;
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

		private boolean target;
		private final Button targetCheckBox;

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
			this.target = false;
			this.isVerbose = false;
			this.isQuiet = false;

			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout());
			
			this.usesStrictValidationCheckBox = this.buildUsesStrictValidationCheckBox(composite, this.buildUsesStrictValidationSelectionListener());
			this.makesReadOnlyCheckBox = this.buildMakesReadOnlyCheckBox(composite, this.buildMakesReadOnlySelectionListener());
			this.suppressesPackageInfoGenCheckBox = this.buildSuppressesPackageInfoGenCheckBox(composite, this.buildSuppressesPackageInfoGenSelectionListener());
			this.suppressesHeaderGenCheckBox = this.buildSuppressesHeaderGenCheckBox(composite, this.buildSuppressesHeaderGenSelectionListener());
			this.targetCheckBox = this.buildTargetCheckBox(composite, this.buildTargetSelectionListener());
			this.isVerboseCheckBox = this.buildIsVerboseCheckBox(composite, this.buildIsVerboseSelectionListener());
			this.isQuietCheckBox = this.buildIsQuietCheckBox(composite, this.buildIsQuietSelectionListener());
		}

		// ********** UI components **********

		private Button buildUsesStrictValidationCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_useStrictValidation);
			checkBox.setSelection(this.usesStrictValidation());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Button buildMakesReadOnlyCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_makeReadOnly);
			checkBox.setSelection(this.makesReadOnly());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Button buildSuppressesPackageInfoGenCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_suppressPackageInfoGen);
			checkBox.setSelection(this.suppressesPackageInfoGen());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
		
		private Button buildSuppressesHeaderGenCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_suppressesHeaderGen);
			checkBox.setSelection(this.suppressesHeaderGen());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
		
		private Button buildTargetCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_target);
			checkBox.setSelection(this.targetIs20());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
	
		private Button buildIsVerboseCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_verbose);
			checkBox.setSelection(this.isVerbose());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
		
		private Button buildIsQuietCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_quiet);
			checkBox.setSelection(this.isQuiet());
			checkBox.addSelectionListener(listener);
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
		
		private SelectionListener buildTargetSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					Options1Composite.this.setTargetIs20(
						Options1Composite.this.targetCheckBox.getSelection());
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
		
		protected boolean targetIs20() {
			return this.target;
		}
		
		protected void setTargetIs20(boolean targetIs20){
			this.target = targetIs20;
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
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_treatsAsXmlSchema);
			checkBox.setSelection(this.treatsAsXmlSchema());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Button buildTreatsAsRelaxNgCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_treatsAsRelaxNg);
			checkBox.setSelection(this.treatsAsRelaxNg());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
		private Button buildTreatsAsRelaxNgCompactCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_treatsAsRelaxNgCompact);
			checkBox.setSelection(this.treatsAsRelaxNgCompact());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Button buildTreatsAsDtdCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_treatsAsDtd);
			checkBox.setSelection(this.treatsAsDtd());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Button buildTreatsAsWsdlCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_treatsAsWsdl);
			checkBox.setSelection(this.treatsAsWsdl());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
	
		private Button buildVersionCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_showsVersion);
			checkBox.setSelection(this.showsVersion());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}
	
		private Button buildHelpCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorOptionsWizardPage_showsHelp);
			checkBox.setSelection(this.showsHelp());
			checkBox.addSelectionListener(listener);
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
