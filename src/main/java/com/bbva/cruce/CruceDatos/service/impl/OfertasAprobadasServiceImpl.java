package com.bbva.cruce.CruceDatos.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbva.cruce.CruceDatos.dao.OfertasAprobadasDao;
import com.bbva.cruce.CruceDatos.model.AplicacionOfertasBean;
import com.bbva.cruce.CruceDatos.model.CargaCabecera;
import com.bbva.cruce.CruceDatos.model.OfertasAprobadasPlantilla;
import com.bbva.cruce.CruceDatos.model.RespuestaWS;
import com.bbva.cruce.CruceDatos.service.OfertasAprobadasService;
import com.bbva.cruce.CruceDatos.util.Constantes;


@Service
public class OfertasAprobadasServiceImpl implements OfertasAprobadasService {

	@Autowired
	private OfertasAprobadasDao ofertasAprobadasDao;
	
	@Override
	public ArrayList<OfertasAprobadasPlantilla> cargarArchivo(byte[] archivo){
		
		ArrayList<OfertasAprobadasPlantilla> ofertasAprobadasList = new ArrayList<OfertasAprobadasPlantilla>();
		OfertasAprobadasPlantilla ofertaAprobada = null;
		DataFormatter formatter = new DataFormatter();
		
		
		try {
			InputStream input = new ByteArrayInputStream(archivo);
			// leer archivo excel
			XSSFWorkbook worbook = new XSSFWorkbook(input);
			//obtener la hoja que se va leer
			XSSFSheet sheet = worbook.getSheetAt(0);
			//obtener todas las filas de la hoja excel
			Iterator<Row> rowIterator = sheet.iterator();
			
			Row row;
			
			// Obviamos las cabeceras 
			if(rowIterator.hasNext()) {
				row = rowIterator.next();		
			}
			
			// se recorre cada fila hasta el final
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				//se obtiene las celdas por fila
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell;
				ofertaAprobada = new OfertasAprobadasPlantilla();
				// se recorre cada fila hasta el final
				while (cellIterator.hasNext()) {
					// se obtiene la celda en específico y se la imprime
					cell = cellIterator.next();
					
					
					switch(cell.getCellTypeEnum()){
						case STRING:
							
							System.out.print(formatter.formatCellValue(cell) +" | ");
							if(cell.getColumnIndex() == 0) {
								ofertaAprobada.setTipoDocumento(formatter.formatCellValue(cell));
							}else{
								ofertaAprobada.setDoi(formatter.formatCellValue(cell));
							}
							break;
						case NUMERIC:
							System.out.print(formatter.formatCellValue(cell)+" | ");
							if(cell.getColumnIndex()== 0) {
								ofertaAprobada.setTipoDocumento(formatter.formatCellValue(cell));
							}else{
								ofertaAprobada.setDoi(formatter.formatCellValue(cell));
							}
							break;
						default:break;
					}
					
				}
				ofertasAprobadasList.add(ofertaAprobada);
				System.out.println();
			}		
			worbook.close();		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return ofertasAprobadasList;
	}
	
	@Override
	public List<OfertasAprobadasPlantilla> guardarArchivo(List<AplicacionOfertasBean> listOfertas, String identificador, String fileName, HttpServletRequest  request,
			String userName) {
		RespuestaWS<List<OfertasAprobadasPlantilla>> respuesta = new RespuestaWS<>();
		List<OfertasAprobadasPlantilla> listOferts = new ArrayList<>();
		String tempIden = "TMP_" + identificador;
		int identity;
		try {
			
			CargaCabecera cargaCabecera = new CargaCabecera();
			cargaCabecera.setUsuario(userName);
			cargaCabecera.setNombreArchivo(fileName);
			cargaCabecera.setFechaCarga(identificador);
			
			identity = ofertasAprobadasDao.cargaCabeceraOferta(cargaCabecera);
			request.getSession().setAttribute("identity", identity);
			
			ofertasAprobadasDao.cargaOfertas(listOfertas);
			
			listOferts = ofertasAprobadasDao.cruceOferta(tempIden, identificador);
			request.getSession().setAttribute("listOferts", listOferts);
			
//			List<OfertasAprobadasPlantilla> listOfertsExcel = (List<OfertasAprobadasPlantilla>) request.getSession().getAttribute("listOferts");
			
			respuesta.setEstado(Constantes.CODE_SUCCESSFUL);
			respuesta.setMensajeFuncional("Se registro en la tabla");
			respuesta.setObjetoRespuesta(listOferts);
			
			
		} catch(Exception e) {
			respuesta.setEstado(Constantes.CODE_WRONG);
			respuesta.setMensajeFuncional("Ocurrio un error en el registro");
			respuesta.setMensajeTecnico(e.getMessage());
			return listOferts;
		}
		return listOferts;
	}

	@Override
	public RespuestaWS<List<OfertasAprobadasPlantilla>> listaOfertasAprobadas(HttpServletRequest  request) {
		RespuestaWS<List<OfertasAprobadasPlantilla>> respuesta = new RespuestaWS<>();
		try {	
			@SuppressWarnings("unchecked")
			List<OfertasAprobadasPlantilla> listOfertsExcel = (List<OfertasAprobadasPlantilla>) request.getSession().getAttribute("listOferts");
			respuesta.setEstado(Constantes.CODE_SUCCESSFUL);
			respuesta.setMensajeFuncional("Se registro en la tabla");
			respuesta.setObjetoRespuesta(listOfertsExcel);
			
		} catch(Exception e) {
			respuesta.setEstado(Constantes.CODE_WRONG);
			respuesta.setMensajeFuncional("Ocurrio un error en el registro");
			respuesta.setMensajeTecnico(e.getMessage());
			return respuesta;
		}

		return respuesta;
	}

	@Override
	public CargaCabecera getCargaUltima(HttpServletRequest request) {
		CargaCabecera cargaCabecera = new CargaCabecera();
		int identity;
		try {
			identity = (int) request.getSession().getAttribute("identity");
			
			cargaCabecera = ofertasAprobadasDao.getCargaCabecera(identity);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return cargaCabecera;
	}

	@Override
	public void deleteDataOfertas(String identOferta) {
		try {
			ofertasAprobadasDao.deleteOfertas(identOferta);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
}
