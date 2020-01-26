$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	
	var korisniciTabela = $('#korisniciTabela');
	
	
	function getK() {
		
	
	
	$.get('KorisniciServlet', function(data) {
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return
		}
		
		if(data.status == 'success'){
			
			console.log(data)
			
			if(data.uloga =='KORISNIK'){
				window.location.replace('Glavna.html');
				return;
			}
			if(data.uloga == 'ADMIN'){
				var podaci = data.sviKorisnici;
				for (i in podaci){
					korisniciTabela.append('<tr>' + 
					'<td><a href="PojedinacniKorisnik.html?korisnickoIme='+podaci[i].korisnickoIme + '">' + podaci[i].korisnickoIme + '</a></td>' + 
					'<td>' + podaci[i].datumRegistracije + '</td>' + 
					'<td>' + podaci[i].uloga + '</td>' + 
				'</tr>')
					}	
				}
			}
		})
	}
	getK()
})