// Constants
const apiUrl = 'http://localhost:9000/status';

// D3.js setup
const margin = { top: 20, right: 20, bottom: 30, left: 50 };
const width = 800 - margin.left - margin.right;
const height = 400 - margin.top - margin.bottom;

// Create SVG element
const svg = d3.select('#graph')
  .append('svg')
  .attr('width', width + margin.left + margin.right)
  .attr('height', height + margin.top + margin.bottom)
  .append('g')
  .attr('transform', `translate(${margin.left}, ${margin.top})`);

// Create scales
const xScale = d3.scaleTime().range([0, width]);
const yScale = d3.scaleLinear().range([height, 0]);

// Create axes
const xAxis = d3.axisBottom(xScale);
const yAxis = d3.axisLeft(yScale);

// Append axes to the SVG
svg.append('g')
  .attr('class', 'x-axis')
  .attr('transform', `translate(0, ${height})`);
svg.append('g').attr('class', 'y-axis');

// Create line generator
const line = d3.line()
  .x((d) => xScale(new Date(d.date)))
  .y((d) => yScale(d.value));

// Initialize data array
let data = [];

// Fetch data from the API
function fetchData() {
  fetch(apiUrl)
    .then(response => response.json())
    .then(newData => {
      // Update data array with the latest values
      data = newData;

      // Update the graph and table
      updateGraph();
      updateTable();
    })
    .catch(error => console.error('Error fetching data:', error));
}

// Function to update the line graph
function updateGraph() {
  // Update scales' domains
  xScale.domain(d3.extent(data, d => new Date(d.date)));
  yScale.domain([0, d3.max(data, d => d.value)]);

  // Update axes
  svg.select('.x-axis').call(xAxis);
  svg.select('.y-axis').call(yAxis);

  // Create or update the line
  const path = svg.selectAll('.line').data([data]);
  path.enter()
    .append('path')
    .attr('class', 'line')
    .attr('fill', 'none')
    .attr('stroke', 'steelblue')
    .attr

