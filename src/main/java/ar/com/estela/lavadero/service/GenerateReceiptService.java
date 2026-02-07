package ar.com.estela.lavadero.service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

import ar.com.estela.lavadero.dto.BillingReceiptDto;
import ar.com.estela.lavadero.dto.GenerateReceiptData;
import ar.com.estela.lavadero.dto.GenerateReceiptDto;
import ar.com.estela.lavadero.interfaces.GenerateReceiptInterface;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerateReceiptService implements GenerateReceiptInterface {

	@Value("${paper-width}")
	private Integer paperWidth;

	@Value("${paper-heigth}")
	private Integer paperHeight;

	@Override
	public ResponseEntity<byte[]> printReceipt(Long randomNum, GenerateReceiptDto receiptDto) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			// conversión mm -> puntos ( (valor / 25.4) * 72 )
			float width = (Float.valueOf(paperWidth) / Float.valueOf("25.4")) * Float.valueOf(72);
			float height = (Float.valueOf(paperHeight) / Float.valueOf("25.4")) * Float.valueOf(72);

			Rectangle pageSize = new Rectangle(width, height);

			Document document = new Document(pageSize);
			PdfWriter.getInstance(document, outputStream);

			document.setMargins(10, 10, 10, 10);

			document.open();

			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 10);

			StringBuilder titleString = new StringBuilder(" ").append(randomNum)
					.append("\n Recibo no valido como factura");

			Paragraph receiptDesc = new Paragraph(titleString.toString(), fontTitle);
			receiptDesc.setAlignment(Element.ALIGN_CENTER);
			document.add(receiptDesc);

			Paragraph title = new Paragraph("Tintoreria Lavadero Europressing", fontTitle);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			document.add(Chunk.NEWLINE);

			LineSeparator lineSeparator = new LineSeparator();
			lineSeparator.setLineWidth(0.3f);
			lineSeparator.setPercentage(50);
			lineSeparator.setAlignment(Element.ALIGN_CENTER);
			document.add(lineSeparator);

			Paragraph address = new Paragraph("Avenida \n Independencia 3636", fontTitle);
			address.setAlignment(Element.ALIGN_CENTER);
			document.add(address);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

			Paragraph todayDate = new Paragraph("Fecha: ".concat(LocalDateTime.now().format(formatter)), fontTitle);
			todayDate.setAlignment(Element.ALIGN_CENTER);
			document.add(todayDate);

			document.add(Chunk.NEWLINE);
			document.add(lineSeparator);

			StringBuilder userAdress = new StringBuilder("Direccion: ").append(receiptDto.getAddress()).append("\n");

			StringBuilder nameAndPhone = new StringBuilder(userAdress).append("Nombre: ").append(receiptDto.getName())
					.append("\n Telefono: ").append(receiptDto.getPhone());

			nameAndPhone.append("\n Tipo de pago: ").append(receiptDto.getPayment());

			Paragraph userData = new Paragraph(nameAndPhone.toString(), fontTitle);
			userData.setAlignment(Element.ALIGN_LEFT);
			document.add(userData);
			document.add(new Paragraph("\n", fontTitle));

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2f, 3f, 1.5f });

			Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 8);

			PdfPCell cell = new PdfPCell(new Phrase("Cantidad", fontHeader));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1f);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Descripcion", fontHeader));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1f);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Precio ($)", fontHeader));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1f);
			table.addCell(cell);

			Integer total = 0;
			NumberFormat numFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));

			for (GenerateReceiptData receiptData : receiptDto.getData()) {
				cell = new PdfPCell(new Phrase(receiptData.getQuantity().toString(), fontHeader));
				cell.setBorderWidth(1f);

				table.addCell(cell);

				cell = new PdfPCell(new Phrase(receiptData.getDescription(), fontHeader));
				cell.setBorderWidth(1f);

				table.addCell(cell);

				total = total + (receiptData.getPrice() * receiptData.getQuantity());

				PdfPCell cellPrice = new PdfPCell(new Phrase(numFormat.format(receiptData.getPrice()), fontHeader));
				cellPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellPrice.setBorderWidth(1f);
				table.addCell(cellPrice);
			}

			document.add(table);

			Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Paragraph titleTotal = new Paragraph("Total: $".concat(numFormat.format(total)), fontTotal);
			titleTotal.setAlignment(Element.ALIGN_RIGHT);
			document.add(Chunk.NEWLINE);
			document.add(titleTotal);

			document.add(new Paragraph("\n", fontTitle));
			document.add(lineSeparator);

			document.close();

			HttpHeaders headersResponse = new HttpHeaders();
			headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recibo.pdf");
			headersResponse.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

			return ResponseEntity.ok().headers(headersResponse).body(outputStream.toByteArray());

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}

	}

	@Override
	public ResponseEntity<byte[]> printBilling(BillingReceiptDto request) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter.getInstance(document, outputStream);

			document.setMargins(10, 10, 10, 10);

			document.open();

			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Paragraph receiptDesc = new Paragraph("Cierre de caja", fontTitle);
			receiptDesc.setAlignment(Element.ALIGN_CENTER);
			document.add(receiptDesc);

			Paragraph title = new Paragraph("Tintoreria Lavadero Europressing", fontTitle);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			document.add(Chunk.NEWLINE);

			LineSeparator lineSeparator = new LineSeparator();
			lineSeparator.setLineWidth(0.3f);
			lineSeparator.setPercentage(50);
			lineSeparator.setAlignment(Element.ALIGN_CENTER);
			document.add(lineSeparator);

			Paragraph address = new Paragraph("Avenida Independencia 3636", fontTitle);
			address.setAlignment(Element.ALIGN_CENTER);
			document.add(address);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

			Paragraph todayDate = new Paragraph("Fecha: ".concat(LocalDateTime.now().format(formatter)), fontTitle);
			todayDate.setAlignment(Element.ALIGN_CENTER);
			document.add(todayDate);

			document.add(Chunk.NEWLINE);
			document.add(lineSeparator);

			StringBuilder firstPart = new StringBuilder("Ordenes: ").append(request.getOrders()).append("\n Prendas: ")
					.append(request.getItems());

			Paragraph userData = new Paragraph(firstPart.toString(), fontTitle);
			userData.setAlignment(Element.ALIGN_LEFT);
			document.add(userData);
			document.add(new Paragraph("\n", fontTitle));
			
			StringBuilder totalDataStr = new StringBuilder("Total Cobrado: ").append(request.getTotal()).append(" Gastos: -")
					.append(request.getSpent()).append(" Faltante: ").append(request.getMissing());

			userData = new Paragraph(totalDataStr.toString(), fontTitle);
			userData.setAlignment(Element.ALIGN_LEFT);
			document.add(userData);
			document.add(new Paragraph("\n", fontTitle));

			document.close();

			HttpHeaders headersResponse = new HttpHeaders();
			headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=facturacion.pdf");
			headersResponse.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

			return ResponseEntity.ok().headers(headersResponse).body(outputStream.toByteArray());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

}
