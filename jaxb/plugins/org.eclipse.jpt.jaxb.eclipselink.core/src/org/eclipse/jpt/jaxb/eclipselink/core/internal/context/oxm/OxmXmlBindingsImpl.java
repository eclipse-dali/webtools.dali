package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;

public class OxmXmlBindingsImpl
		extends AbstractJaxbContextNode
		implements OxmXmlBindings {
	
	protected EXmlBindings eXmlBindings;
	
	public final static String PACKAGE_NAME_PROPERTY = "packageName"; //$NON-NLS-1$
	protected String packageName;
	
	
	public OxmXmlBindingsImpl(OxmFile parent, EXmlBindings eXmlBindings) {
		super(parent);
		this.eXmlBindings = eXmlBindings;
		this.packageName = buildPackageName();
	}
	
	
	// ***** package name *****
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public void setPackageName(String packageName) {
		this.eXmlBindings.setPackageName(packageName);
		setPackageName_(packageName);
	}
	
	protected void setPackageName_(String packageName) {
		String oldPackageName = this.packageName;
		this.packageName = packageName;
		firePropertyChanged(PACKAGE_NAME_PROPERTY, oldPackageName, packageName);
	}
	
	protected String buildPackageName() {
		return this.eXmlBindings.getPackageName();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eXmlBindings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
}
