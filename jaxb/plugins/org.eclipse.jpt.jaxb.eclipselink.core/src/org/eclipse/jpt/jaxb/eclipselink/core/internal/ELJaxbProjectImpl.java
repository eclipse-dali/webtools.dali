package org.eclipse.jpt.jaxb.eclipselink.core.internal;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;

public class ELJaxbProjectImpl
		extends AbstractJaxbProject
		implements ELJaxbProject {
	
	public ELJaxbProjectImpl(JaxbProject.Config config) {
		super(config);
	}
	
	
	public Iterable<JptXmlResource> getOxmResources() {
		return new TransformationIterable<JaxbFile, JptXmlResource>(
				getJaxbFiles(Oxm.CONTENT_TYPE)) {
			@Override
			protected JptXmlResource transform(JaxbFile o) {
				return (JptXmlResource) o.getResourceModel();
			}
		};
	}
}
