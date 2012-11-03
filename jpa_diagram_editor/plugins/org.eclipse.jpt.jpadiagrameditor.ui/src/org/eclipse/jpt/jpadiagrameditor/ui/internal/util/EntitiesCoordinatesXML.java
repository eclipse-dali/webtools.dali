package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class EntitiesCoordinatesXML {
	
	private Document document;
	private Element rootElement;
	private String projectName;
	
	public static final String XML_ELEMENT_POSITION = "\n\t\t"; //$NON-NLS-1$
		
	public EntitiesCoordinatesXML(String projectName){
		this.projectName = projectName;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		InputStream is = null;
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			is = (InputStream)findXMLFile(true);
			document = documentBuilder.parse(is);
			rootElement = document.getDocumentElement();
		} catch (ParserConfigurationException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotParseFileErrorMSG, e);
		} catch (SAXException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotParseFileErrorMSG, e);
		} catch (IOException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotReadFileErrorMSG, e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// ignore
				}
		}
	}

	
    private Closeable findXMLFile(boolean inputStream) throws FileNotFoundException{
    	IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			IResource[] resources = project.members();
			for (IResource res : resources) {
				if (res instanceof IFolder) {
					IFile existingXMLFile = ((IFolder) res).getFile(projectName + "." + ModelIntegrationUtil.DIAGRAM_XML_FILE_EXTENSION); //$NON-NLS-1$
					if (existingXMLFile != null && existingXMLFile.exists()) {
						return inputStream ? 
								new FileInputStream(new File(existingXMLFile.getLocationURI())) :
									new FileOutputStream(new File(existingXMLFile.getLocationURI()));
					}
				}
			}
		} catch (CoreException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotObtainProjectErrorMSG, e);
		}
		IFile existingXMLFile = project.getFile(ModelIntegrationUtil.getDiagramsXMLFolderPath(project).append(projectName).addFileExtension(ModelIntegrationUtil.DIAGRAM_XML_FILE_EXTENSION));
		return inputStream ? new FileInputStream(new File(existingXMLFile.getLocationURI())) :
									new FileOutputStream(new File(existingXMLFile.getLocationURI()));
    }
	
    synchronized public void store() {
    	IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    	Diagram diagram = ModelIntegrationUtil.getDiagramByProject(project);
		List<Shape> picts = diagram.getChildren();
		Iterator<Shape> it = picts.iterator();
		// collecting data from the saved pictograms
		document.removeChild(rootElement);
		rootElement = document.createElement("entities"); //$NON-NLS-1$
		document.appendChild(rootElement);

		while (it.hasNext()) {
			Shape pict = it.next();
			String name = Graphiti.getPeService().getPropertyValue(pict, JPAEditorConstants.PROP_ENTITY_CLASS_NAME);

			RoundedRectangle rect = (RoundedRectangle) pict.getGraphicsAlgorithm();

			boolean isPrimaryCollapsed = JPAEditorConstants.TRUE_STRING.equals(Graphiti.getPeService().getPropertyValue(pict,
							JPAEditorConstants.PRIMARY_COLLAPSED));
			boolean isRelationCollapsed = JPAEditorConstants.TRUE_STRING.equals(Graphiti.getPeService().getPropertyValue(pict,
							JPAEditorConstants.RELATION_COLLAPSED));
			boolean isBasicCollapsed = JPAEditorConstants.TRUE_STRING.equals(Graphiti.getPeService().getPropertyValue(pict,
							JPAEditorConstants.BASIC_COLLAPSED));

			Element entity = createEntityElementTag(document, rootElement, JPAEditorConstants.ENTITY_XML_TAG, XML_ELEMENT_POSITION);
			Text nameText = document.createTextNode(name);
			Element entityName = document.createElement(JPAEditorConstants.ENTITY_NAME_TAG);
			createChildElementTag(document, nameText, entityName, entity, XML_ELEMENT_POSITION);

			Text width = document.createTextNode(String.valueOf(rect.getWidth()));
			Element entityWidth = document.createElement(JPAEditorConstants.ENTITY_WIDTH_TAG);
			createChildElementTag(document, width, entityWidth, entity, XML_ELEMENT_POSITION);

			Text height = document.createTextNode(String.valueOf(rect.getHeight()));
			Element entityHeight = document.createElement(JPAEditorConstants.ENTITY_HEIGHT_TAG);
			createChildElementTag(document, height, entityHeight, entity, XML_ELEMENT_POSITION);

			Text xCoordinate = document.createTextNode(String.valueOf(rect.getX()));
			Element entityXCoordinate = document.createElement(JPAEditorConstants.ENTITY_X_COORDINATE_TAG);
			createChildElementTag(document, xCoordinate, entityXCoordinate,	entity, XML_ELEMENT_POSITION);

			Text yCoordinate = document.createTextNode(String.valueOf(rect.getY()));
			Element entityYCoordinate = document.createElement(JPAEditorConstants.ENTITY_Y_COORDINATE_TAG);
			createChildElementTag(document, yCoordinate, entityYCoordinate,	entity, XML_ELEMENT_POSITION);
			
			Text primaryCollapsed = document.createTextNode(String.valueOf(isPrimaryCollapsed));
			Element entityPrimaryCollapsed = document.createElement(JPAEditorConstants.ENTITY_PRIMARY_SECTION_STATE_TAG);
			createChildElementTag(document, primaryCollapsed, entityPrimaryCollapsed, entity, XML_ELEMENT_POSITION);	
			
			Text relationCollapsed = document.createTextNode(String.valueOf(isRelationCollapsed));
			Element entityRelationCollapsed = document.createElement(JPAEditorConstants.ENTITY_RELATION_SECTION_STATE_TAG);
			createChildElementTag(document, relationCollapsed, entityRelationCollapsed,	entity, XML_ELEMENT_POSITION);
			
			Text basicCollapsed = document.createTextNode(String.valueOf(isBasicCollapsed));
			Element entityBasicCollapsed = document.createElement(JPAEditorConstants.ENTITY_BASIC_SECTION_STATE_TAG);
			createChildElementTag(document, basicCollapsed, entityBasicCollapsed,	entity, "\n\t");	 //$NON-NLS-1$
			
			rootElement.appendChild(document.createTextNode("\n")); //$NON-NLS-1$

		}
		ModelIntegrationUtil.setXmiExists(false);
	}

	synchronized public void load(Hashtable<String, SizePosition> marks){
		if(rootElement.getChildNodes().getLength()<=1){
			if(ModelIntegrationUtil.xmiExists()){
				store();
				save();
			}
		} 
		
		NodeList nodeList = rootElement.getElementsByTagName(JPAEditorConstants.ENTITY_XML_TAG);
		for(int i=0; i<nodeList.getLength();i++){
			Element node = (Element) nodeList.item(i);
			String entityName = node.getElementsByTagName(JPAEditorConstants.ENTITY_NAME_TAG).item(0).getTextContent().trim();
			int width = Integer.parseInt(node.getElementsByTagName(JPAEditorConstants.ENTITY_WIDTH_TAG).item(0).getTextContent().trim());
			int height = Integer.parseInt(node.getElementsByTagName(JPAEditorConstants.ENTITY_HEIGHT_TAG).item(0).getTextContent().trim());
			int xCoordinate = Integer.parseInt(node.getElementsByTagName(JPAEditorConstants.ENTITY_X_COORDINATE_TAG).item(0).getTextContent().trim());
			int yCoordinate = Integer.parseInt(node.getElementsByTagName(JPAEditorConstants.ENTITY_Y_COORDINATE_TAG).item(0).getTextContent().trim());
			boolean isPrimaryCollapsed = Boolean.parseBoolean(node.getElementsByTagName(JPAEditorConstants.ENTITY_PRIMARY_SECTION_STATE_TAG).item(0).getTextContent().trim());
			boolean isRelationCollapsed = Boolean.parseBoolean(node.getElementsByTagName(JPAEditorConstants.ENTITY_RELATION_SECTION_STATE_TAG).item(0).getTextContent().trim());
			boolean isBasicCollapsed = Boolean.parseBoolean(node.getElementsByTagName(JPAEditorConstants.ENTITY_BASIC_SECTION_STATE_TAG).item(0).getTextContent().trim());
			
			SizePosition rectSP = new SizePosition(width, height, xCoordinate, yCoordinate);
			rectSP.primaryCollapsed = isPrimaryCollapsed;
			rectSP.relationCollapsed = isRelationCollapsed;
			rectSP.basicCollapsed = isBasicCollapsed;
			
			marks.put(entityName, rectSP);
		}
		
	}
	
	private Element createEntityElementTag(Document document, Element scheduleElement, String descr, String position) {
		Element job = document.createElement(descr);
		scheduleElement.appendChild(document.createTextNode("\n\t")); //$NON-NLS-1$
		scheduleElement.appendChild(job);
		job.appendChild(document.createTextNode(position));
		
		return job;
	}
	
	private void createChildElementTag(Document document, Text nameText, Element nameEl, Element parentElement, String position) {
		parentElement.appendChild(nameEl);
		nameEl.appendChild(nameText);
		parentElement.appendChild(document.createTextNode(position));
	}
	
	public void save() {
		OutputStream os = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			os = (OutputStream)findXMLFile(false);
			transformer.transform(new DOMSource(document), new StreamResult(os));
			os.flush();
		} catch (Exception e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotCreateDOMFileErrorMSG, e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				// ignore
			}
		}		
	}
	
	synchronized public void close() {
		save();
		clean();
	}
	
	synchronized public void clean() {
		try {
	    	IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);			
	    	project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			JPADiagramEditorPlugin.logError(JPAEditorMessages.EntitiesCoordinatesXML_CannotRefrfreshFile, e);
		}		
		document = null;
		rootElement = null;
	}


}
