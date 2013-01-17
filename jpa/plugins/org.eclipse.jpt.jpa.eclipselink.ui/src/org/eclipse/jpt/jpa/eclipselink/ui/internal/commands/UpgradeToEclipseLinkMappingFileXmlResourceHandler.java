/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.ui.handlers.HandlerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Handler used to upgrade an orm.xml file to an eclipselink orm.xml file
 * when the selected object adapts to a <code>JptXmlResource</code>.
 * See org.eclipse.jpt.jpa.eclipselink.ui/plugin.xml
 */
public class UpgradeToEclipseLinkMappingFileXmlResourceHandler 
	extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection 	= (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		for (Object selectedObject : selection.toArray()) {
			this.upgradeToEclipseLinkMappingFile(selectedObject);
		}
		return null;
	}

	protected void upgradeToEclipseLinkMappingFile(Object selectedObject) {
		upgradeToEclipseLinkMappingFile(this.adaptSelection(selectedObject));
	}

	protected JptXmlResource adaptSelection(Object selectedObject) {
		return PlatformTools.getAdapter(selectedObject, JptXmlResource.class);
	}

	protected static void upgradeToEclipseLinkMappingFile(JptXmlResource xmlResource) {
		IProject project = xmlResource.getFile().getProject();
		JpaProject jpaProject = getJpaProject(project);
		if (jpaProject == null) {
			throw new IllegalStateException("Missing JPA project: " + project.getName()); //$NON-NLS-1$
		}
		String newVersion = jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(XmlEntityMappings.CONTENT_TYPE).getVersion();
		String fileLocation = xmlResource.getFile().getRawLocation().toOSString();
			
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

	private static JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	protected static String buildSchemaLocationString(String namespace, String schemaLocation) {
		return namespace + ' ' + schemaLocation;
	}

	protected static String getNamespace() {
		return EclipseLink.SCHEMA_NAMESPACE;
	}

	protected static String getSchemaLocationForVersion(String schemaVersion) {
		String schemaLocation =  XmlEntityMappings.SCHEMA_LOCATIONS.get(schemaVersion);
		if (schemaLocation == null) {
			JptJpaEclipseLinkUiPlugin.instance().logError(new Throwable("No schema location defined for version: " + schemaVersion)); //$NON-NLS-1$
		}
		return schemaLocation;
	}
}