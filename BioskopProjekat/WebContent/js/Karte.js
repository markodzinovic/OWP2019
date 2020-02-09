$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var tabelaKarti = $('#tabelaKarti');
	var admin = $('#admin');
	var ad = $('#a');
	var ko = $('#k');
	


	
	$.get('KarteServlet', function(data) {
		
		console.log(data.status);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		if(data.status == 'success'){
			console.log(data);
		
			var karte = data.sveKarte;
			
			if(data.uloga == 'KORISNIK'){
				admin.hide();
				ad.hide();
				
				prikazKarata(karte,tabelaKarti);
				$('td:nth-child(8)').hide();
				return;
			}
			if(data.uloga == 'ADMIN'){
				admin.show();
				ko.hide();
				
				prikazKarata(karte,tabelaKarti);
				return;
			}
		}
	})
	
	function prikazKarata(karteA,tabelaAdmin) {
		
		for ( k in karteA) {
			tabelaKarti.append('<tr>'+
					'<td><a href="PojedinacniFilm.html?id='+karteA[k].projekcija.nazivFilma.id+'" >' + karteA[k].projekcija.nazivFilma.naziv + '</td>' + 
					'<td><a href="PojedinacnaProjekcija.html?id='+karteA[k].projekcija.id+'" >' + karteA[k].projekcija.datum + '</td>' +
					'<td><a href="PojedinacnaKarta.html?id='+karteA[k].id+'" >' + karteA[k].datum + '</td>' +
					'<td>' + karteA[k].projekcija.tipProjekcije.naziv + '</td>' +
					'<td>' + karteA[k].projekcija.sala.naziv + '</td>' +
					'<td>' + karteA[k].sediste + '</td>' +
					'<td>' + karteA[k].projekcija.cena + 'e'+ '</td>' +
					'<td><a href="PojedinacniKorisnik.html?id='+karteA[k].korisnik.korisnickoIme+'" >' + karteA[k].korisnik.korisnickoIme + '</td>' +
			'</tr>')
		}
	}
		
})