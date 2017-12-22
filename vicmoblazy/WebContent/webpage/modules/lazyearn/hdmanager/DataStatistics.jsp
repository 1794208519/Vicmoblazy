<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/webpage/modules/lazyearn/include/head.jsp"%>
</head>
<body style="padding-top: 20px">
	<div class="row">
		<div class="col-lg-8">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">硬件销量</h4>
					<div id="main" style="width: 100% !important; height: 600px"></div>
				</div>
			</div>
		</div>
		<div class="col-lg-4">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">硬件反馈</h4>
					<div class="row">
						<!-- .col -->
						<div style="width: 100% !important; height: 600px">

							<div class="col-xlg-12">
								<div class="card card-body">
									<div class="row" style="height: 140px">
										<div class="col-lg-3 text-center">
											<a href="app-contact-detail.html"><img
												src="../assets/images/users/1.jpg" alt="user"
												class="img-circle img-responsive"></a>
										</div>
										<div class="col-lg-9">
											<h3 class="box-title m-b-0">Johnathan Doe</h3>
											<small>Web Designer</small>
											<address>
												795 Folsom Ave, Suite 600 San Francisco, CADGE 94107 <br />
												<br /> <abbr title="Phone">P:</abbr> (123) 456-7890
											</address>
										</div>
									</div>
								</div>
							</div>
							<div class="col-xlg-12">
								<div class="card card-body">
									<div class="row" style="height: 140px">
										<div class="col-lg-3 text-center">
											<a href="app-contact-detail.html"><img
												src="../assets/images/users/1.jpg" alt="user"
												class="img-circle img-responsive"></a>
										</div>
										<div class="col-lg-9">
											<h3 class="box-title m-b-0">Johnathan Doe</h3>
											<small>Web Designer</small>
											<address>
												795 Folsom Ave, Suite 600 San Francisco, CADGE 94107 <br />
												<br /> <abbr title="Phone">P:</abbr> (123) 456-7890
											</address>
										</div>
									</div>
								</div>
							</div>
							<div class="col-xlg-12">
								<div class="card card-body">
									<div class="row" style="height: 140px">
										<div class="col-lg-3 text-center">
											<a href="app-contact-detail.html"><img
												src="../assets/images/users/1.jpg" alt="user"
												class="img-circle img-responsive"></a>
										</div>
										<div class="col-lg-9">
											<h3 class="box-title m-b-0">Johnathan Doe</h3>
											<small>Web Designer</small>
											<address>
												795 Folsom Ave, Suite 600 San Francisco, CADGE 94107 <br />
												<br /> <abbr title="Phone">P:</abbr> (123) 456-7890
											</address>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-12">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">硬件销量饼图</h4>
					 <div id="pie-chart" style="width:100%; height:400px;"></div>
				</div>
			</div>
		</div>
		
		<div class="col-lg-12">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">懒赚功能统计图</h4>
					<div id="starts" style="width: 100%; height: 400px;"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxStatic}/asset/plugins/echarts/echarts.min.js"
		charset="utf-8"></script>
	<script src="${ctxStatic}/asset/plugins/echarts/china.js"
		charset="utf-8"></script>
	<script src="${ctxStatic}/asset/plugins/echarts/echarts-initi.js"></script>
	
</body>
</html>