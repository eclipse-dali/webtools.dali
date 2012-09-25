/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.ui.handlers.HandlerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpgradeToEclipseLinkMappingFileHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection 	= (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			upgradeToEclipseLinkOrm(selectedObject);
		}
		return null;
	}

	protected void upgradeToEclipseLinkOrm(Object selectedObject) {
		JptXmlResource xmlResource = PlatformTools.getAdapter(selectedObject, JptXmlResource.class);
		if (xmlResource == null) {
			XmlFile xmlFile = PlatformTools.getAdapter(selectedObject, XmlFile.class);
			if (xmlFile != null) {
				xmlResource = xmlFile.getXmlResource();
			}
		}
		if (xmlResource == null) {
			return;
		}

		JpaProject jpaProject = this.getJpaProject(xmlResource.getFile().getProject());
		String fileLocation = xmlResource.getFile().getRawLocation().toOSString();
		String newVersion = jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(XmlEntityMappings.CONTENT_TYPE).getVersion();
			
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fileLocation));
			document.setXmlStandalone(true);
			NodeList nodes = document.getElementsByTagName("entity-mappings"); //$NON-NLS-1$
			if (nodes.getLength() > 0) {
				// we know only one "entity-mappings" element exists in the mapping file
				Node node = nodes.item(0);
				NamedNodeMap attributes = node.getAttributes();
				attributes.getNamedItem("version").setTextContent(newVersion); //$NON-NLS-1$
				attributes.getNamedItem("xmlns").setTextContent(getNamespace()); //$NON-NLS-1$
				attributes.getNamedItem("xsi:schemaLocation").setTextContent(buildSchemaLocationString(getNamespace(), getSchemaLocationForVersion(newVersion))); //$NON-NLS-1$
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.transform(new DOMSource(document), new StreamResult(new File(fileLocation)));
			
			// refresh the file to load the changes to the editor
			xmlResource.getFile().refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (ParserConfigurationException pce) {
			JptJpaEclipseLinkUiPlugin.instance().logError(pce);
		} catch (SAXException sxe) {
			JptJpaEclipseLinkUiPlugin.instance().logError(sxe);
		} catch (IOException ioe) {
			JptJpaEclipseLinkUiPlugin.instance().logError(ioe);
		} catch (TransformerConfigurationException tce) {
			JptJpaEclipseLinkUiPlugin.instance().logError(tce);
		} catch (TransformerException te) {
			JptJpaEclipseLinkUiPlugin.instance().logError(te);
		} catch (CoreException ce) {
			JptJpaEclipseLinkUiPlugin.instance().logError(ce);
		}
	}

	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	protected static String buildSchemaLocationString(String namespace, String schemaLocation) {
		return namespace + ' ' + schemaLocation;
	}

	protected String getNamespace() {
		return EclipseLink.SCHEMA_NAMESPACE;
	}

	protected String getSchemaLocationForVersion(String schemaVersion) {
		return SCHEMA_LOCATIONS.get(schemaVersion);
	}

	private static HashMap<String, String> SCHEMA_LOCATIONS = buildSchemaLocations();

	private static HashMap<String, String> buildSchemaLocations() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(EclipseLink.SCHEMA_VERSION, EclipseLink.SCHEMA_LOCATION);
		map.put(EclipseLink1_1.SCHEMA_VERSION, EclipseLink1_1.SCHEMA_LOCATION);
		map.put(EclipseLink1_2.SCHEMA_VERSION, EclipseLink1_2.SCHEMA_LOCATION);
		map.put(EclipseLink2_0.SCHEMA_VERSION, EclipseLink2_0.SCHEMA_LOCATION);
		map.put(EclipseLink2_1.SCHEMA_VERSION, EclipseLink2_1.SCHEMA_LOCATION);
		map.put(EclipseLink2_2.SCHEMA_VERSION, EclipseLink2_2.SCHEMA_LOCATION);
		map.put(EclipseLink2_3.SCHEMA_VERSION, EclipseLink2_3.SCHEMA_LOCATION);
		map.put(EclipseLink2_4.SCHEMA_VERSION, EclipseLink2_4.SCHEMA_LOCATION);
		return map;
	}

}