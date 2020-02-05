$(document).ready(function() {
	
	var tabelaAdmin = $('#karteAdmin');
	var tabelaKorisnici = $('#karteKorisnik');
	
	$.get('KarteServlet', function(data) {
		
		console.log(data);
		
		var karte = data.sveKarte;

		for ( k in karte) {
			tabelaAdmin.append('<tr>'+
					'<td>' + karte[k].projekcija.nazivFilma.naziv + '</td>' + 
					'<td>' + karte[k].datum + '</td>' +
					'<td>' + karte[k].projekcija.tipProjekcije.naziv + '</td>' +
					'<td>' + karte[k].projekcija.sala.naziv + '</td>' +
					'<td>' + karte[k].sediste + '</td>' +
					'<td>' + karte[k].projekcija.cena + '</td>' +
					'<td>' + karte[k].korisnik.korisnickoIme + '</td>' +
			'</tr>')
		}
		
		for ( k in karte) {
			tabelaKorisnici.append('<tr>'+
					'<td>' + karte[k].projekcija.nazivFilma.naziv + '</td>' + 
					'<td>' + karte[k].datum + '</td>' +
					'<td>' + karte[k].projekcija.tipProjekcije.naziv + '</td>' +
					'<td>' + karte[k].projekcija.sala.naziv + '</td>' +
					'<td>' + karte[k].sediste + '</td>' +
					'<td>' + karte[k].projekcija.cena + '</td>' +
			'</tr>')
		}
		
	})
	
})