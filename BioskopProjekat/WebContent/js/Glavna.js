$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var tabelaProjekcije = $('#projekcijaTabela')
	var admin = $('#admin');
	var korisnik = $('#korisnik');

	korisnik.hide();
	admin.hide();
	
	$.get('GlavnaServlet', function(data) {
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		if(data.status == 'success'){
			console.log(data.uloga);
			
			var projekcije = data.sveProjekcije;
			for ( i in projekcije) {
				tabelaProjekcije.append('<tr>'+
						'<td><a href="PojedinacniFilm.html?id='+ projekcije[i].nazivFilma.id +'">' + projekcije[i].nazivFilma.naziv + '</td>'+
						'<td><a href="PojedinacnaProjekcija.html?id='+ projekcije[i].id +'">' + projekcije[i].datum + '</td>' +
						'<td>' + projekcije[i].tipProjekcije.naziv + '</td>' +
						'<td>' + projekcije[i].sala.naziv + '</td>' +
						'<td>' + projekcije[i].cena + 'e'  + '</td>' +
						'<td><a  href="KupovinaKarte.html?id='+projekcije[i].id+'">Kupi kartu</a></td>' +
				'</tr>')
			}
			
			
			
			if(data.uloga == 'KORISNIK'){
				korisnik.show();
				return;
			}
			
			
			if(data.uloga == 'ADMIN'){
				korisnik.hide();
				admin.show();
				return;
			}
		}
		
	})
	
})