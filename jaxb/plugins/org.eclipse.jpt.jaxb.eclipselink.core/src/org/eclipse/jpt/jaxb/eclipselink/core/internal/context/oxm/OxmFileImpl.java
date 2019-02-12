/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFileDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmFileImpl 
		extends AbstractJaxbContextNode
		implements OxmFile {
	
	protected OxmFileDefinition definition;
	
	// never null
	protected JptXmlResource oxmResource;
	
	/**
	 * The resource type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JptResourceType resourceType;
	
	/** backpointer */
	protected ELJaxbPackage elJaxbPackage;
	
	/**
	 * The root element of the oxm file.
	 */
	protected OxmXmlBindings xmlBindings;

	
	
	public OxmFileImpl(ELJaxbContextRoot parent, JptXmlResource oxmResource) {
		super(parent);
		this.definition = new OxmFileDefinitionImpl();
		this.oxmResource = oxmResource;
		this.resourceType = oxmResource.getResourceType();
		this.xmlBindings = buildXmlBindings();
	}
	
	
	@Override
	public ELJaxbContextRoot getContextRoot() {
		return (ELJaxbContextRoot) super.getContextRoot();
	}
	
	@Override
	public IResource getResource() {
		return this.oxmResource.getFile();
	}
	
	public OxmFileDefinition getDefinition() {
		return this.definition;
	}
	
	public JptXmlResource getOxmResource() {
		return this.oxmResource;
	}
	
	public String getPackageName() {
		return (this.xmlBindings == null) ? null : this.xmlBindings.getPackageName();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.resourceType = this.oxmResource.getResourceType();
		syncXmlBindings();
	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.xmlBindings != null) {
			this.xmlBindings.update();
		}
	}
	
	
	// ***** package *****
	
	public ELJaxbPackage getJaxbPackage() {
		return this.elJaxbPackage;
	}
	
	public void setPackage(ELJaxbPackage newPackage) {
		ELJaxbPackage oldPackage = this.elJaxbPackage;
		this.elJaxbPackage = newPackage;
		firePropertyChanged(PACKAGE_PROPERTY, oldPackage, newPackage);
	}
	
	
	// ***** xml bindings *****
	
	public OxmXmlBindings getXmlBindings() {
		return this.xmlBindings;
	}
	
	protected void setXmlBindings(OxmXmlBindings xmlBindings) {
		OxmXmlBindings oldXmlBindings = this.xmlBindings;
		this.xmlBindings = xmlBindings;
		firePropertyChanged(XML_BINDINGS_PROPERTY, oldXmlBindings, xmlBindings);
	}
	
	protected void syncXmlBindings() {
		EXmlBindings eXmlBindings = (EXmlBindings) this.oxmResource.getRootObject();
		if (this.xmlBindings == null || this.xmlBindings.getEXmlBindings() != eXmlBindings) {
			setXmlBindings(buildXmlBindings());
		}
		if (this.xmlBindings != null) {
			this.xmlBindings.synchronizeWithResourceModel();
		}
	}
	
	protected OxmXmlBindings buildXmlBindings() {
		EXmlBindings eXmlBindings = (EXmlBindings) this.oxmResource.getRootObject();
		return (eXmlBindings == null) ? null : new OxmXmlBindingsImpl(this, eXmlBindings);
	}
	
	
	// ***** validation *****
	
	protected EXmlBindings getEXmlBindings() {
		// each doc will at least have an xml-bindings node, by definition
		return (EXmlBindings) this.oxmResource.getRootObject();
	}
	
	@Override
	public TextRange getValidationTextRange() {
		return getEXmlBindings().getValidationTextRange();
	}
	
	protected TextRange getVersionTextRange() {
		return getEXmlBindings().getVersionTextRange();
	}
	
	protected TextRange getPackageNameTextRange() {
		return getEXmlBindings().getPackageNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (getJaxbPackage() == null) {
			if (StringTools.isBlank(getPackageName())) {
				messages.add(
						this.buildValidationMessage(
								getPackageNameTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.OXM_FILE__NO_PACKAGE_SPECIFIED
							));
			}
			else {
				messages.add(
						this.buildValidationMessage(
								getPackageNameTextRange(),
								JptJaxbEclipseLinkCoreValidationMessages.OXM_FILE__NO_SUCH_PACKAGE,
								getPackageName()));
			}
		}
		
		if (this.xmlBindings != null) {
			this.xmlBindings.validate(messages, reporter);
		}
	}
}
