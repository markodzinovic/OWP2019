$(document).ready(function() {
	
	
	var korisniciTabela = $('#korisniciTabela');
	
	
	function getK() {
		
	
	
	$.get('KorisniciServlet', function(data) {
		
		console.log(data)

			var podaci = data.sviKorisnici;
			for (i in podaci){
				korisniciTabela.append('<tr>' + 
				'<td><a href="PojedinacniKorisnik.html?korisnickoIme='+podaci[i].korisnickoIme + '">' + podaci[i].korisnickoIme + '</a></td>' + 
				'<td>' + podaci[i].datumRegistracije + '</td>' + 
				'<td>' + podaci[i].uloga + '</td>' + 
			'</tr>')
			}
		
	})
	}
	getK()
})