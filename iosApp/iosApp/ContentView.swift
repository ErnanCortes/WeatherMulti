import SwiftUI
import Shared

struct ContentView: View {
    @State private var getContent = false
    @State private var response: ApiResponse?
    @StateObject private var locationManager = LocationManager()

    
    var body: some View {
        

        VStack {
            Button("Request Permission") {
                locationManager.requestPermission()
            }
            
            Text(String(format: "%.1f°C", response?.current.temp_c ?? 0.0))
            Text(
                (response?.forecast.forecastday ?? [])
                    .compactMap {
                        let day = formatDate($0.date)
                        let temp = String(format: "%.1f", $0.day.avgtemp_c)
                        return "\(day): \(temp)°C"
                    }
                    .joined(separator: "\n")
            )
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .task {
            do {
                let weatherResponse = try await WeatherRepository().getWeather(cords: "55.75,37.62")
                await MainActor.run {
                    response = weatherResponse
                }
            } catch {
                print(error.localizedDescription)
            }
        }
        
        
    }
}

func formatDate(_ dateString: String) -> String {
    let inputFormatter = DateFormatter()
    inputFormatter.dateFormat = "yyyy-MM-dd"
    
    let outputFormatter = DateFormatter()
    outputFormatter.dateFormat = "d MMM" // Example: 30 May

    if let date = inputFormatter.date(from: dateString) {
        return outputFormatter.string(from: date)
    } else {
        return dateString // fallback if parsing fails
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
